package kr.toxicity.hud.bukkit.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Marker interface for all custom BetterHud Bukkit events.
 * Provides a shared static {@link HandlerList} so the Bukkit event system
 * can dispatch and unregister listeners for all BetterHud events uniformly.
 */
public interface BetterHudEvent {

    /**
     * Shared handler list used by all BetterHud events.
     */
    HandlerList HANDLER_LIST = new HandlerList();
}
