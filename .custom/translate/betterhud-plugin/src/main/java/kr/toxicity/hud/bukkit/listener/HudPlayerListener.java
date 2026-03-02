package kr.toxicity.hud.bukkit.listener;

import kr.toxicity.hud.bukkit.BetterHudPlugin;
import kr.toxicity.hud.bukkit.event.HudPlayerJoinEvent;
import kr.toxicity.hud.bukkit.event.HudPlayerQuitEvent;
import kr.toxicity.hud.bukkit.player.HudPlayerBukkit;
import kr.toxicity.hud.manager.PlayerManagerImpl;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

/**
 * Bukkit event listener that tracks players joining and leaving the server.
 * Creates a {@link HudPlayerBukkit} instance on join and removes it on quit.
 */
public class HudPlayerListener implements Listener {

    /** The plugin instance used for scheduling and configuration access. */
    private final BetterHudPlugin plugin;

    /**
     * Constructs the listener.
     *
     * @param plugin the owning {@link BetterHudPlugin}
     */
    public HudPlayerListener(BetterHudPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a {@link HudPlayerBukkit} when a player joins and fires {@link HudPlayerJoinEvent}.
     *
     * @param event the Bukkit join event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        BossBar.Color color = kr.toxicity.hud.manager.ConfigManagerImpl.INSTANCE.getBarColor();
        HudPlayerBukkit hudPlayer = new HudPlayerBukkit(event.getPlayer(), color);
        PlayerManagerImpl.INSTANCE.addPlayer(hudPlayer);
        hudPlayer.startTick();
        plugin.getServer().getPluginManager().callEvent(new HudPlayerJoinEvent(event.getPlayer()));
    }

    /**
     * Saves and removes the {@link HudPlayerBukkit} when a player quits, then fires
     * {@link HudPlayerQuitEvent}.
     *
     * @param event the Bukkit quit event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        plugin.getServer().getPluginManager().callEvent(new HudPlayerQuitEvent(event.getPlayer()));
        PlayerManagerImpl.INSTANCE.removePlayer(event.getPlayer().getUniqueId());
    }

    /**
     * Triggers the initial plugin reload once the server has fully started.
     *
     * @param event the server-load event
     */
    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
                kr.toxicity.hud.api.BetterHudAPI.inst().reload());
    }
}
