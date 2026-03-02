package kr.toxicity.hud.api.popup;

/**
 * Identifies the order in which popups within a group are shown.
 */
public enum PopupSortType {

    /** Show the popup that is spatially nearest to the player. */
    NEAREST,

    /** Show the popup that is spatially farthest from the player. */
    FARTHEST,

    /** Show the most recently triggered popup first. */
    LATEST,

    /** Show the oldest active popup first. */
    OLDEST
}
