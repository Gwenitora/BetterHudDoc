package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.hud.Hud;

import java.util.Collection;

/**
 * Provides access to all loaded {@link Hud} definitions.
 */
public interface HudManager {

    /**
     * Returns the {@link Hud} with the given name, or {@code null} if not found.
     *
     * @param name the HUD name
     * @return the matching {@link Hud}, or {@code null}
     */
    Hud getHud(String name);

    /**
     * Returns the names of all currently loaded HUDs.
     *
     * @return an unmodifiable collection of HUD names
     */
    Collection<String> getAllNames();

    /**
     * Returns all currently loaded {@link Hud} instances.
     *
     * @return an unmodifiable collection of HUDs
     */
    Collection<Hud> getAllHuds();
}
