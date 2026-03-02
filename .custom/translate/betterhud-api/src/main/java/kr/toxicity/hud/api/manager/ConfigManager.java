package kr.toxicity.hud.api.manager;

/**
 * Exposes global configuration values read from {@code config.yml}.
 */
public interface ConfigManager {

    /**
     * Verbosity levels for BetterHud's debug output.
     */
    enum DebugLevel {
        /** Log only manager lifecycle events. */
        MANAGER,
        /** Log asset loading events (default). */
        ASSETS,
        /** Log every individual file loaded. */
        FILE,
        /** Log everything. */
        ALL
    }

    /**
     * Returns {@code true} if debug logging is enabled.
     *
     * @return {@code true} when in debug mode
     */
    boolean debug();

    /**
     * Returns the number of boss-bar lines allocated for the HUD.
     *
     * @return the boss-bar line count
     */
    int getBossbarLine();

    /**
     * Returns the current debug verbosity level.
     *
     * @return the {@link DebugLevel}
     */
    DebugLevel getDebugLevel();

    /**
     * Checks whether the given {@link DebugLevel} is active and logs if it is.
     * Used by managers to conditionally emit verbose output.
     *
     * @param level the level to check
     * @return {@code true} if the level is enabled
     */
    static boolean checkAvailable(DebugLevel level) {
        ConfigManager cm = kr.toxicity.hud.api.BetterHudAPI.inst().getConfigManager();
        return cm.debug() && cm.getDebugLevel().ordinal() >= level.ordinal();
    }
}
