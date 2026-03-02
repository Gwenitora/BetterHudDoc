package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.plugin.ReloadInfo;

import java.io.File;

/**
 * Lifecycle interface implemented by every BetterHud manager.
 * The plugin calls these methods in a defined order during startup and reload.
 */
public interface BetterHudManager {

    /**
     * Called once when the plugin first enables.
     * Use this for one-time setup that should not repeat on reload.
     */
    void start();

    /**
     * Called at the beginning of a reload, before any files are read.
     * Use this to clear cached state so the reload starts clean.
     */
    void preReload();

    /**
     * Called during the main reload phase.
     * The manager should read its configuration from {@code workingDir} and rebuild its state.
     *
     * @param workingDir the plugin data folder
     * @param info       metadata about the current reload (flags, sender)
     */
    void reload(File workingDir, ReloadInfo info);

    /**
     * Called after all managers have completed their {@link #reload} phase.
     * Use this to wire up cross-manager references.
     */
    void postReload();

    /**
     * Called when the plugin disables.
     * Use this to release resources, close connections, etc.
     */
    void end();

    /**
     * Returns a human-readable name for this manager, used in log output.
     *
     * @return the manager name
     */
    String getManagerName();
}
