package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.database.HudDatabase;
import kr.toxicity.hud.api.database.HudDatabaseConnector;

/**
 * Manages the active {@link HudDatabase} and available connectors.
 */
public interface DatabaseManager {

    /**
     * Returns the currently active database instance.
     *
     * @return the active {@link HudDatabase}
     */
    HudDatabase getCurrentDatabase();

    /**
     * Registers a named database connector so it can be selected in {@code config.yml}.
     *
     * @param name      the connector identifier (used in config)
     * @param connector the {@link HudDatabaseConnector} to register
     */
    void addDatabase(String name, HudDatabaseConnector connector);
}
