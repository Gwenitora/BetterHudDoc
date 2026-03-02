package kr.toxicity.hud.api.player;

import kr.toxicity.hud.api.adapter.LocationWrapper;

import java.util.Objects;

/**
 * A named world location that is tracked by the compass system.
 *
 * @param name     the display name of this waypoint
 * @param location the world location of this waypoint
 */
public record PointedLocation(String name, LocationWrapper location) {

    /**
     * Compact constructor – validates that neither field is null.
     *
     * @param name     the waypoint name
     * @param location the waypoint location
     */
    public PointedLocation {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(location, "location must not be null");
    }
}
