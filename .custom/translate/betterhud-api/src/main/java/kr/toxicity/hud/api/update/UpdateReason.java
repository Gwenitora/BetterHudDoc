package kr.toxicity.hud.api.update;

import java.util.Objects;

/**
 * Pairs a named reason string with an {@link UpdateEvent}.
 * Used to communicate why a popup or HUD element was updated.
 *
 * @param name  human-readable reason identifier
 * @param event the associated {@link UpdateEvent}
 */
public record UpdateReason(String name, UpdateEvent event) {

    /**
     * Compact constructor – validates that neither field is null.
     *
     * @param name  the reason name
     * @param event the associated event
     */
    public UpdateReason {
        Objects.requireNonNull(name, "reason name must not be null");
        Objects.requireNonNull(event, "event must not be null");
    }
}
