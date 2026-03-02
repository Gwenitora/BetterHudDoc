package kr.toxicity.hud.api.update;

/**
 * Marker interface for events that trigger HUD or popup updates.
 * Implementations carry event-specific data (e.g. the amount of damage taken).
 */
public interface UpdateEvent {

    /**
     * A no-op event used when no real event data is available (e.g. periodic tick updates).
     */
    UpdateEvent EMPTY = new UpdateEvent() {};
}
