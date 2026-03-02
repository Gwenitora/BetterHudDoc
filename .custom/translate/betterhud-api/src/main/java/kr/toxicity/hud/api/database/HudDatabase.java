package kr.toxicity.hud.api.database;

import kr.toxicity.hud.api.player.HudPlayer;

/**
 * Persistent storage backend for player HUD data.
 * Implementations must be safe to call from async threads.
 */
public interface HudDatabase extends AutoCloseable {

    /**
     * Loads persisted data for the given player, applying it to the player's state.
     *
     * @param player the player to load data for
     */
    void load(HudPlayer player);

    /**
     * Persists the current state of the given player to the backend.
     *
     * @param player the player to save
     */
    void save(HudPlayer player);

    /**
     * Returns {@code true} if this database connection has been closed.
     *
     * @return {@code true} when closed
     */
    boolean isClosed();
}
