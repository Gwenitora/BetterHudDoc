package kr.toxicity.hud.api.player;

import java.util.Set;

/**
 * Supplies the set of compass waypoints visible to a specific player.
 * Implementations may query databases, plugin hooks, or custom logic.
 */
@FunctionalInterface
public interface PointedLocationProvider {

    /**
     * Returns the set of {@link PointedLocation} entries that should be visible to {@code player}.
     *
     * @param player the requesting player
     * @return a non-null (possibly empty) set of waypoints
     */
    Set<PointedLocation> provide(HudPlayer player);
}
