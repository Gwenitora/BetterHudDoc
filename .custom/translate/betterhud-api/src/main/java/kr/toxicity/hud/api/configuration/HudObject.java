package kr.toxicity.hud.api.configuration;

/**
 * Base interface shared by all loadable HUD objects (HUDs, popups, compasses).
 * Each implementation is loaded from a YAML configuration file and uniquely identified.
 */
public interface HudObject {

    /**
     * Unique identifier record used as a map key.
     *
     * @param type the type descriptor of this object
     * @param name the name within its type namespace
     */
    record Identifier(HudObjectType<?> type, String name) {}

    /**
     * Returns the name of this object as defined in its configuration file.
     *
     * @return the object name
     */
    String getName();

    /**
     * Returns {@code true} if this object is active for all players by default.
     *
     * @return {@code true} when this is a default object
     */
    boolean isDefault();

    /**
     * Returns the type descriptor for this object.
     *
     * @return the {@link HudObjectType}
     */
    HudObjectType<?> getType();

    /**
     * Returns the update tick interval for this object.
     * A value of {@code 1} means update every tick.
     *
     * @return tick interval
     */
    long tick();

    /**
     * Returns the composite identifier of this object.
     *
     * @return the {@link Identifier}
     */
    default Identifier identifier() {
        return new Identifier(getType(), getName());
    }
}
