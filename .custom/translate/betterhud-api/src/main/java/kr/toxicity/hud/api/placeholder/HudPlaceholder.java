package kr.toxicity.hud.api.placeholder;

import kr.toxicity.hud.api.player.HudPlayer;

/**
 * Evaluates a placeholder value for the given player.
 *
 * @param <T> the type of value produced (e.g. {@link Double}, {@link Boolean}, {@link String})
 */
@FunctionalInterface
public interface HudPlaceholder<T> {

    /**
     * Invokes this placeholder for the given player.
     *
     * @param player the player context
     * @return the placeholder value
     */
    T invoke(HudPlayer player);
}
