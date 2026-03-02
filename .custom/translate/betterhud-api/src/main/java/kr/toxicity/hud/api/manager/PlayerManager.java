package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.player.PointedLocationProvider;

import java.util.Collection;
import java.util.UUID;

/**
 * Manages active {@link HudPlayer} instances and compass waypoint providers.
 */
public interface PlayerManager {

    /**
     * Returns the {@link HudPlayer} for the given UUID, or {@code null} if not online.
     *
     * @param uuid the player UUID
     * @return the {@link HudPlayer}, or {@code null}
     */
    HudPlayer getHudPlayer(UUID uuid);

    /**
     * Returns all currently online {@link HudPlayer} instances.
     *
     * @return an unmodifiable collection of online players
     */
    Collection<HudPlayer> getAllHudPlayer();

    /**
     * Registers a {@link PointedLocationProvider} that supplies compass waypoints.
     *
     * @param provider the provider to add
     */
    void addLocationProvider(PointedLocationProvider provider);
}
