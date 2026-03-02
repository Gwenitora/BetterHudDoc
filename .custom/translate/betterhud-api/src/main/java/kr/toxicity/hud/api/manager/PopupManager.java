package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.popup.Popup;

import java.util.Collection;

/**
 * Provides access to all loaded {@link Popup} definitions.
 */
public interface PopupManager {

    /**
     * Returns the {@link Popup} with the given name, or {@code null} if not found.
     *
     * @param name the popup name
     * @return the matching {@link Popup}, or {@code null}
     */
    Popup getPopup(String name);

    /**
     * Returns the names of all currently loaded popups.
     *
     * @return an unmodifiable collection of popup names
     */
    Collection<String> getAllNames();

    /**
     * Returns all currently loaded {@link Popup} instances.
     *
     * @return an unmodifiable collection of popups
     */
    Collection<Popup> getAllPopups();
}
