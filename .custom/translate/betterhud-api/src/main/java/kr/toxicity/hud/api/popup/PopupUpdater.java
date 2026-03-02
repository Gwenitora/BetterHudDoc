package kr.toxicity.hud.api.popup;

/**
 * Controls a specific popup display instance for a player.
 * Returned when a popup is shown to a player via {@link Popup#show}.
 */
public interface PopupUpdater {

    /**
     * Forces an immediate update of the popup's displayed content.
     */
    void update();

    /**
     * Returns the current display index of this popup instance.
     *
     * @return the index
     */
    int getIndex();

    /**
     * Sets the display index of this popup instance.
     *
     * @param index the new index
     */
    void setIndex(int index);

    /**
     * Removes / hides this popup instance from the player.
     */
    void remove();
}
