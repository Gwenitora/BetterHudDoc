package kr.toxicity.hud.api;

import java.util.Objects;

/**
 * Holds the singleton {@link BetterHud} instance.
 * The implementation sets itself here during initialisation.
 */
public final class BetterHudAPI {

    /** The registered singleton instance. */
    private static BetterHud instance;

    /** Prevent instantiation. */
    private BetterHudAPI() {}

    /**
     * Registers the singleton instance.
     * Called once by the implementation module during plugin enable.
     *
     * @param impl the {@link BetterHud} implementation to register
     * @throws NullPointerException if {@code impl} is {@code null}
     */
    public static void register(BetterHud impl) {
        instance = Objects.requireNonNull(impl, "BetterHud implementation must not be null");
    }

    /**
     * Returns the registered singleton.
     *
     * @return the active {@link BetterHud} instance
     * @throws IllegalStateException if the plugin has not been enabled yet
     */
    public static BetterHud inst() {
        if (instance == null) {
            throw new IllegalStateException("BetterHud has not been initialised yet.");
        }
        return instance;
    }
}
