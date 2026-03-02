package kr.toxicity.hud.api.listener;

import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.update.UpdateEvent;

/**
 * Functional interface for HUD condition listeners.
 * Returns {@code true} when the associated HUD element should be visible.
 */
@FunctionalInterface
public interface HudListener {

    /**
     * Evaluates whether the HUD condition is met for the given player and event.
     *
     * @param player the player to check for
     * @param event  the triggering event
     * @return {@code true} if the condition passes and the HUD element should be shown
     */
    boolean check(HudPlayer player, UpdateEvent event);
}
