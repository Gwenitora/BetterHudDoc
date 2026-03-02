package kr.toxicity.hud.api;

import kr.toxicity.hud.api.manager.*;
import kr.toxicity.hud.api.plugin.ReloadFlagType;
import kr.toxicity.hud.api.plugin.ReloadState;

import java.util.function.Consumer;

/**
 * Main interface of BetterHud.
 * Provides access to all managers and lifecycle methods.
 * Obtain the singleton via {@link #getInstance()}.
 */
public interface BetterHud {

    /** Default resource-pack namespace used for all generated assets. */
    String DEFAULT_NAMESPACE = "betterhud";

    /**
     * Returns the running BetterHud singleton.
     *
     * @return the active {@link BetterHud} instance
     */
    static BetterHud getInstance() {
        return BetterHudAPI.inst();
    }

    /**
     * Triggers a full plugin reload (resource pack regeneration + manager reload).
     *
     * @param args optional flags controlling reload behaviour
     * @return the resulting {@link ReloadState}
     */
    ReloadState reload(ReloadFlagType... args);

    /**
     * Returns {@code true} while a reload is in progress.
     *
     * @return {@code true} if the plugin is currently reloading
     */
    boolean isOnReload();

    /**
     * Returns the platform-specific bootstrap that wired this instance.
     *
     * @return the active {@link BetterHudBootstrap}
     */
    BetterHudBootstrap getBootstrap();

    /**
     * Returns the HUD manager.
     *
     * @return {@link HudManager}
     */
    HudManager getHudManager();

    /**
     * Returns the popup manager.
     *
     * @return {@link PopupManager}
     */
    PopupManager getPopupManager();

    /**
     * Returns the compass manager.
     *
     * @return {@link CompassManager}
     */
    CompassManager getCompassManager();

    /**
     * Returns the player manager.
     *
     * @return {@link PlayerManager}
     */
    PlayerManager getPlayerManager();

    /**
     * Returns the configuration manager.
     *
     * @return {@link ConfigManager}
     */
    ConfigManager getConfigManager();

    /**
     * Returns the shader manager.
     *
     * @return {@link ShaderManager}
     */
    ShaderManager getShaderManager();

    /**
     * Returns the database manager.
     *
     * @return {@link DatabaseManager}
     */
    DatabaseManager getDatabaseManager();

    /**
     * Returns the placeholder manager.
     *
     * @return {@link PlaceholderManager}
     */
    PlaceholderManager getPlaceholderManager();

    /**
     * Returns the listener manager.
     *
     * @return {@link ListenerManager}
     */
    ListenerManager getListenerManager();

    /**
     * Returns the trigger manager.
     *
     * @return {@link TriggerManager}
     */
    TriggerManager getTriggerManager();

    /**
     * Returns the text manager.
     *
     * @return {@link TextManager}
     */
    TextManager getTextManager();

    /**
     * Registers a callback that will be invoked at the start of every reload.
     *
     * @param task the {@link Runnable} to invoke
     */
    void addReloadStartTask(Runnable task);

    /**
     * Registers a callback that will be invoked at the end of every reload.
     *
     * @param task the {@link Consumer} receiving the resulting {@link ReloadState}
     */
    void addReloadEndTask(Consumer<ReloadState> task);
}
