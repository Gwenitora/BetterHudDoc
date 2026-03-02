package kr.toxicity.hud.nms;

import kr.toxicity.hud.api.volatilecode.VolatileCodeHandler;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * Extends {@link VolatileCodeHandler} with version-specific NMS operations.
 * All implementations access server internals exclusively via reflection to avoid
 * compile-time coupling with any specific NMS/CraftBukkit version.
 */
public interface NMSHandler extends VolatileCodeHandler {

    /**
     * Returns the detected NMS version this handler was built for.
     *
     * @return the {@link NMSVersion}
     */
    NMSVersion getVersion();

    /**
     * Sends a boss-bar update packet directly to the player using NMS.
     * The boss bar is used as the rendering surface for HUD components.
     *
     * @param player    the target Bukkit player
     * @param component the Adventure {@link Component} to display in the boss bar
     * @param color     the boss-bar {@link BossBar.Color} to use
     */
    void sendBossBarPacket(Player player, Component component, BossBar.Color color);

    /**
     * Removes the injected HUD boss bar from the player's screen.
     *
     * @param player the target Bukkit player
     */
    void removeBossBar(Player player);

    /**
     * Returns a Folia-safe reference to the given player.
     * On non-Folia servers this simply returns the same player object.
     *
     * @param player the Bukkit player to adapt
     * @return the adapted (or unchanged) player reference
     */
    Player getFoliaAdaptedPlayer(Player player);
}
