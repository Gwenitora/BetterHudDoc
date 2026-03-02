package kr.toxicity.hud.api.player;

import kr.toxicity.hud.api.adapter.LocationWrapper;
import kr.toxicity.hud.api.adapter.WorldWrapper;
import kr.toxicity.hud.api.component.WidthComponent;
import kr.toxicity.hud.api.configuration.HudObject;
import kr.toxicity.hud.api.popup.PopupIteratorGroup;
import kr.toxicity.hud.api.popup.PopupUpdater;
import net.kyori.adventure.bossbar.BossBar;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a player whose HUD is managed by BetterHud.
 * Each online player has exactly one {@link HudPlayer} instance for the duration of their session.
 */
public interface HudPlayer {

    /**
     * Returns the player's unique identifier.
     *
     * @return player UUID
     */
    UUID uuid();

    /**
     * Returns the player's display name.
     *
     * @return player name
     */
    String name();

    /**
     * Returns the player's current location.
     *
     * @return the wrapped location
     */
    LocationWrapper location();

    /**
     * Returns the player's current world.
     *
     * @return the wrapped world
     */
    WorldWrapper world();

    /**
     * Returns the platform-specific player handle (e.g. Bukkit {@code Player}).
     *
     * @return the handle object
     */
    Object handle();

    /**
     * Returns the current HUD component being displayed in the boss bar.
     *
     * @return the active {@link WidthComponent}
     */
    WidthComponent getHudComponent();

    /**
     * Returns {@code true} if HUD updates are currently enabled for this player.
     *
     * @return {@code true} when HUD is enabled
     */
    boolean isHudEnabled();

    /**
     * Enables or disables HUD updates for this player.
     *
     * @param enabled {@code true} to enable
     */
    void setHudEnabled(boolean enabled);

    /**
     * Returns the current tick counter value (incremented on each update cycle).
     *
     * @return the tick count
     */
    long getTick();

    /**
     * Cancels all running HUD tasks for this player and cleans up state.
     */
    void cancel();

    /**
     * Forces an immediate HUD update for this player.
     */
    void update();

    /**
     * Starts the periodic tick task for this player.
     */
    void startTick();

    /**
     * Cancels the periodic tick task without full cleanup.
     */
    void cancelTick();

    /**
     * Returns the popup iterator group map keyed by group name.
     *
     * @return mutable map of group name → {@link PopupIteratorGroup}
     */
    Map<String, PopupIteratorGroup> getPopupGroupIteratorMap();

    /**
     * Returns the per-player variable map used for template substitution.
     *
     * @return mutable map of variable name → value
     */
    Map<String, String> getVariableMap();

    /**
     * Returns the active popup-key → updater map for deduplication.
     *
     * @return mutable map of key → {@link PopupUpdater}
     */
    Map<Object, PopupUpdater> getPopupKeyMap();

    /**
     * Returns the current boss-bar color assigned to this player.
     *
     * @return the {@link BossBar.Color}
     */
    BossBar.Color getBarColor();

    /**
     * Returns the currently active HUD objects for this player.
     *
     * @return set of active {@link HudObject} instances
     */
    Set<HudObject> getHudObjects();

    /**
     * Returns the set of pointed locations visible to this player's compass.
     *
     * @return set of {@link PointedLocation} entries
     */
    Set<PointedLocation> getPointedLocation();

    /**
     * Reloads this player's HUD state (re-creates renderers after a plugin reload).
     */
    void reload();

    /**
     * Persists this player's state to the active database.
     */
    void save();

    /**
     * Sets the boss-bar color used for HUD rendering for this player.
     *
     * @param color the new {@link BossBar.Color}
     */
    void setBarColor(BossBar.Color color);
}
