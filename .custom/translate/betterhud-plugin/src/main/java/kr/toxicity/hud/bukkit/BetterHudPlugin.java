package kr.toxicity.hud.bukkit;

import kr.toxicity.hud.BetterHudImpl;
import kr.toxicity.hud.api.BetterHudBootstrap;
import kr.toxicity.hud.api.BetterHudLogger;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.scheduler.HudScheduler;
import kr.toxicity.hud.api.version.MinecraftVersion;
import kr.toxicity.hud.api.volatilecode.VolatileCodeHandler;
import kr.toxicity.hud.bukkit.listener.HudPlayerListener;
import kr.toxicity.hud.bukkit.scheduler.BukkitScheduler;
import kr.toxicity.hud.manager.PlayerManagerImpl;
import kr.toxicity.hud.nms.NMSHandler;
import kr.toxicity.hud.nms.NMSLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Bukkit plugin main class and {@link BetterHudBootstrap} implementation.
 * Entry point for BetterHud on Spigot, Paper, and Folia servers.
 *
 * <p>This class wires together all subsystems:
 * <ol>
 *   <li>Detects the Minecraft version and loads the NMS handler.</li>
 *   <li>Creates the {@link BetterHudImpl} core which registers the API singleton.</li>
 *   <li>Registers the Bukkit event listener for player join/quit.</li>
 *   <li>Triggers the initial async reload on server start.</li>
 * </ol>
 * </p>
 */
public final class BetterHudPlugin extends JavaPlugin implements BetterHudBootstrap {

    /** Core BetterHud implementation instance. */
    private BetterHudImpl core;

    /** Version-specific NMS handler loaded via reflection. */
    private NMSHandler nmsHandler;

    /** Platform task scheduler (Bukkit or Folia-aware). */
    private HudScheduler hudScheduler;

    /** Detected Minecraft version of the running server. */
    private MinecraftVersion minecraftVersion;

    /** Whether this server is running Folia. */
    private final boolean folia = detectFolia();

    /** Whether this server is running Paper (or Folia, which is Paper-based). */
    private final boolean paper = folia || detectPaper();

    /** Simple BetterHudLogger backed by the plugin's java.util.logging Logger. */
    private final BetterHudLogger hudLogger = new BetterHudLogger() {
        private final Logger log = getLogger();
        @Override public void info(String msg)   { log.info(msg); }
        @Override public void warn(String msg)   { log.warning(msg); }
        @Override public void severe(String msg) { log.severe(msg); }
    };

    // -------------------------------------------------------------------------
    // JavaPlugin lifecycle
    // -------------------------------------------------------------------------

    /**
     * Called by Bukkit when the plugin is enabled.
     * Detects version, loads NMS, creates the core, and registers event listeners.
     */
    @Override
    public void onEnable() {
        // Parse server Minecraft version
        String rawVersion = getServer().getBukkitVersion(); // e.g. "1.21.4-R0.1-SNAPSHOT"
        int dash = rawVersion.indexOf('-');
        String stripped = dash > 0 ? rawVersion.substring(0, dash) : rawVersion;
        try {
            minecraftVersion = MinecraftVersion.parse(stripped);
        } catch (Exception e) {
            minecraftVersion = MinecraftVersion.V1_21_4;
            getLogger().warning("Could not parse Minecraft version '" + stripped + "', defaulting to 1.21.4");
        }

        // Load NMS handler
        try {
            nmsHandler = NMSLoader.load();
            getLogger().info("Loaded NMS handler for " + nmsHandler.getVersion());
        } catch (Exception e) {
            getLogger().severe("Failed to load NMS handler: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Create scheduler
        hudScheduler = new BukkitScheduler(this);

        // Initialise core (registers API singleton, starts managers)
        core = new BetterHudImpl(this);

        // Register event listeners
        getServer().getPluginManager().registerEvents(new HudPlayerListener(this), this);

        getLogger().info("BetterHud enabled (MC " + minecraftVersion + ").");
    }

    /**
     * Called by Bukkit when the plugin is disabled.
     * Saves all player data, cancels tasks, and closes the database.
     */
    @Override
    public void onDisable() {
        if (core != null) {
            PlayerManagerImpl.INSTANCE.getAllHudPlayer().forEach(HudPlayer::save);
            PlayerManagerImpl.INSTANCE.end();
        }
        getLogger().info("BetterHud disabled.");
    }

    // -------------------------------------------------------------------------
    // BetterHudBootstrap
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public BetterHudLogger logger() { return hudLogger; }

    /** {@inheritDoc} */
    @Override public File dataFolder() { return getDataFolder(); }

    /** {@inheritDoc} */
    @Override public HudScheduler scheduler() { return hudScheduler; }

    /** {@inheritDoc} */
    @Override public VolatileCodeHandler volatileCode() { return nmsHandler; }

    /** {@inheritDoc} */
    @Override public InputStream resource(String path) { return getResource(path); }

    /** {@inheritDoc} */
    @Override public MinecraftVersion minecraftVersion() { return minecraftVersion; }

    /** {@inheritDoc} */
    @Override public boolean isPaper() { return paper; }

    /** {@inheritDoc} */
    @Override public boolean isFolia() { return folia; }

    /**
     * {@inheritDoc}
     * Sends the current resource pack to a single player.
     */
    @Override
    public void sendResourcePack(HudPlayer player) {
        // TODO: send the resource-pack URL / bytes to the specific player.
    }

    /**
     * {@inheritDoc}
     * Sends the current resource pack to all online players.
     */
    @Override
    public void sendResourcePack() {
        getServer().getOnlinePlayers().forEach(p -> {
            HudPlayer hp = PlayerManagerImpl.INSTANCE.getHudPlayer(p.getUniqueId());
            if (hp != null) sendResourcePack(hp);
        });
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Detects if the running server is Folia by checking for the Folia-specific scheduler class.
     *
     * @return {@code true} if Folia is detected
     */
    private static boolean detectFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Detects if the running server is Paper (but not Folia) by checking for the Paper API class.
     *
     * @return {@code true} if Paper is detected
     */
    private static boolean detectPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("io.papermc.paper.configuration.Configuration");
                return true;
            } catch (ClassNotFoundException e2) {
                return false;
            }
        }
    }
}
