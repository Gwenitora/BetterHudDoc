package kr.toxicity.hud.api;

import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.scheduler.HudScheduler;
import kr.toxicity.hud.api.version.MinecraftVersion;
import kr.toxicity.hud.api.volatilecode.VolatileCodeHandler;

import java.io.File;
import java.io.InputStream;

/**
 * Platform-specific bootstrap service.
 * Provides access to file I/O, logging, scheduling, and version information
 * in a platform-agnostic way so the core module never imports Bukkit types.
 */
public interface BetterHudBootstrap {

    /**
     * Returns the plugin logger.
     *
     * @return the {@link BetterHudLogger} for this platform
     */
    BetterHudLogger logger();

    /**
     * Returns the plugin's data folder (where config.yml is stored).
     *
     * @return the data {@link File} directory
     */
    File dataFolder();

    /**
     * Returns the platform task scheduler.
     *
     * @return the {@link HudScheduler}
     */
    HudScheduler scheduler();

    /**
     * Returns the volatile (NMS) code handler for this server version.
     *
     * @return the {@link VolatileCodeHandler}
     */
    VolatileCodeHandler volatileCode();

    /**
     * Opens a resource bundled inside the plugin JAR.
     *
     * @param path path relative to the JAR root (e.g. {@code "config.yml"})
     * @return the resource stream, or {@code null} if not found
     */
    InputStream resource(String path);

    /**
     * Returns the detected Minecraft version of the running server.
     *
     * @return the {@link MinecraftVersion}
     */
    MinecraftVersion minecraftVersion();

    /**
     * Returns {@code true} if the server is running Paper (or a fork such as Folia).
     *
     * @return {@code true} for Paper-based servers
     */
    boolean isPaper();

    /**
     * Returns {@code true} if the server is running Folia.
     *
     * @return {@code true} for Folia servers
     */
    boolean isFolia();

    /**
     * Sends the generated resource pack to a specific player.
     *
     * @param player the target {@link HudPlayer}
     */
    void sendResourcePack(HudPlayer player);

    /**
     * Sends the generated resource pack to all online players.
     */
    void sendResourcePack();
}
