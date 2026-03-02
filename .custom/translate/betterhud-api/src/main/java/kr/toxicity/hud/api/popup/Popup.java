package kr.toxicity.hud.api.popup;

import kr.toxicity.hud.api.configuration.HudObject;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.update.UpdateEvent;

/**
 * A popup definition loaded from configuration.
 * Popups are transient HUD elements triggered by game events (e.g. damage, kills).
 */
public interface Popup extends HudObject {

    /**
     * Shows this popup to the given player, triggered by {@code event}.
     *
     * @param event  the event that triggered this popup
     * @param player the target player
     * @return a {@link PopupUpdater} to control the instance, or {@code null} if the popup could not be shown
     */
    PopupUpdater show(UpdateEvent event, HudPlayer player);

    /**
     * Hides this popup from the given player immediately.
     *
     * @param player the target player
     */
    void hide(HudPlayer player);

    /**
     * Returns the group name that controls stacking behaviour.
     *
     * @return the group name string
     */
    String getGroupName();

    /**
     * Returns the maximum number of instances of this popup that can be stacked simultaneously.
     *
     * @return max stack count
     */
    int getMaxStack();

    /**
     * Returns the animation frame type for this popup.
     *
     * @return the frame type string
     */
    String frameType();
}
