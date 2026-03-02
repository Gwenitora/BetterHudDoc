package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.database.HudDatabase;
import kr.toxicity.hud.api.database.HudDatabaseConnector;
import kr.toxicity.hud.api.manager.DatabaseManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Singleton implementation of {@link DatabaseManager}.
 * Manages the active database connection and the registry of available connectors.
 */
public class DatabaseManagerImpl implements BetterHudManager, DatabaseManager {

    /** Singleton instance. */
    public static final DatabaseManagerImpl INSTANCE = new DatabaseManagerImpl();

    /** Currently open database, or {@code null} before first reload. */
    private HudDatabase currentDatabase;

    /** Registry of connector factories keyed by their config identifier. */
    private final Map<String, HudDatabaseConnector> connectors = new HashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private DatabaseManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /**
     * Closes the current database before a reload so it can be re-opened
     * with updated configuration.
     */
    @Override
    public void preReload() {
        if (currentDatabase != null && !currentDatabase.isClosed()) {
            try { currentDatabase.close(); } catch (Exception ignored) {}
            currentDatabase = null;
        }
    }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) { /* nothing – uses config */ }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /**
     * {@inheritDoc}
     * Closes the active database on plugin shutdown.
     */
    @Override
    public void end() {
        if (currentDatabase != null && !currentDatabase.isClosed()) {
            try { currentDatabase.close(); } catch (Exception ignored) {}
        }
    }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Database"; }

    // -------------------------------------------------------------------------
    // DatabaseManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public HudDatabase getCurrentDatabase() { return currentDatabase; }

    /** {@inheritDoc} */
    @Override
    public void addDatabase(String name, HudDatabaseConnector connector) {
        connectors.put(
                Objects.requireNonNull(name, "name must not be null"),
                Objects.requireNonNull(connector, "connector must not be null")
        );
    }

    /**
     * Sets the active database (called during reload when config specifies a backend).
     *
     * @param db the new active {@link HudDatabase}
     */
    public void setCurrentDatabase(HudDatabase db) {
        this.currentDatabase = db;
    }
}
