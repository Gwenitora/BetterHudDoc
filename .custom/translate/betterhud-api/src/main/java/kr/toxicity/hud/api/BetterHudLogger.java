package kr.toxicity.hud.api;

/**
 * Simple logging facade used by BetterHud internals.
 * Abstracts over the platform logger so the API module does not import Bukkit.
 */
public interface BetterHudLogger {

    /**
     * Logs an informational message.
     *
     * @param message the message to log
     */
    void info(String message);

    /**
     * Logs a warning message.
     *
     * @param message the message to log
     */
    void warn(String message);

    /**
     * Logs a severe/error message.
     *
     * @param message the message to log
     */
    void severe(String message);
}
