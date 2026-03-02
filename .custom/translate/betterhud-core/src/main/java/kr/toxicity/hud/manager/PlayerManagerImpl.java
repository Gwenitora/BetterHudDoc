package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.PlayerManager;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.player.PointedLocationProvider;
import kr.toxicity.hud.api.plugin.ReloadInfo;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton implementation of {@link PlayerManager}.
 * Thread-safe: uses a {@link ConcurrentHashMap} for the player map.
 */
public class PlayerManagerImpl implements BetterHudManager, PlayerManager {

    /** Singleton instance. */
    public static final PlayerManagerImpl INSTANCE = new PlayerManagerImpl();

    /** UUID → active HudPlayer map. Concurrent to allow async access. */
    private final ConcurrentHashMap<UUID, HudPlayer> playerMap = new ConcurrentHashMap<>();

    /** Registered compass waypoint providers. */
    private final List<PointedLocationProvider> locationProviders = new ArrayList<>();

    /** Private – use {@link #INSTANCE}. */
    private PlayerManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void preReload() { /* keep players – reload their state in postReload */ }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) { /* nothing */ }

    /**
     * {@inheritDoc}
     * After all managers reload, refreshes the HUD state of every online player.
     */
    @Override
    public void postReload() {
        playerMap.values().forEach(HudPlayer::reload);
    }

    /**
     * {@inheritDoc}
     * Cancels all player tasks before the plugin shuts down.
     */
    @Override
    public void end() {
        playerMap.values().forEach(HudPlayer::cancel);
        playerMap.clear();
    }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Player"; }

    // -------------------------------------------------------------------------
    // PlayerManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public HudPlayer getHudPlayer(UUID uuid) { return playerMap.get(uuid); }

    /** {@inheritDoc} */
    @Override
    public Collection<HudPlayer> getAllHudPlayer() { return Collections.unmodifiableCollection(playerMap.values()); }

    /** {@inheritDoc} */
    @Override
    public void addLocationProvider(PointedLocationProvider provider) {
        locationProviders.add(Objects.requireNonNull(provider, "provider must not be null"));
    }

    // -------------------------------------------------------------------------
    // Package-private helpers for the Bukkit plugin layer
    // -------------------------------------------------------------------------

    /**
     * Registers a new player instance (called on player join).
     *
     * @param player the newly created {@link HudPlayer}
     */
    public void addPlayer(HudPlayer player) {
        playerMap.put(player.uuid(), player);
    }

    /**
     * Removes and cancels a player instance (called on player quit).
     *
     * @param uuid the player's UUID
     */
    public void removePlayer(UUID uuid) {
        HudPlayer p = playerMap.remove(uuid);
        if (p != null) p.cancel();
    }

    /**
     * Returns all registered waypoint providers.
     *
     * @return unmodifiable list of {@link PointedLocationProvider}
     */
    public List<PointedLocationProvider> getLocationProviders() {
        return Collections.unmodifiableList(locationProviders);
    }
}
