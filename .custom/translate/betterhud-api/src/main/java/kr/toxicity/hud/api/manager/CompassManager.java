package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.compass.Compass;

import java.util.Collection;

/**
 * Provides access to all loaded {@link Compass} definitions.
 */
public interface CompassManager {

    /**
     * Returns the {@link Compass} with the given name, or {@code null} if not found.
     *
     * @param name the compass name
     * @return the matching {@link Compass}, or {@code null}
     */
    Compass getCompass(String name);

    /**
     * Returns the names of all currently loaded compasses.
     *
     * @return an unmodifiable collection of compass names
     */
    Collection<String> getAllNames();

    /**
     * Returns all currently loaded {@link Compass} instances.
     *
     * @return an unmodifiable collection of compasses
     */
    Collection<Compass> getAllCompasses();
}
