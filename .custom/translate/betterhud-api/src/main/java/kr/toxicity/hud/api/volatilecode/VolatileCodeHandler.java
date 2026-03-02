package kr.toxicity.hud.api.volatilecode;

import kr.toxicity.hud.api.player.HudPlayer;
import net.kyori.adventure.bossbar.BossBar;

/**
 * Provides access to version-specific server internals (NMS) at runtime.
 * Implementations use reflection to avoid compile-time coupling with a specific NMS revision.
 */
public interface VolatileCodeHandler {

    /**
     * Injects the HUD boss bar into the player connection for the given bar color.
     * The boss bar is used as the rendering surface for all HUD elements.
     *
     * @param player the target {@link HudPlayer}
     * @param color  the {@link BossBar.Color} to claim as the HUD channel
     */
    void inject(HudPlayer player, BossBar.Color color);
}
