package kr.toxicity.hud.bukkit.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Fired asynchronously when a BetterHud reload begins.
 * Listeners can use this to prepare external integrations before managers reset their state.
 */
public class PluginReloadStartEvent extends Event implements BetterHudEvent {

    /** Required by Bukkit's event system. */
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs the event, marking it as asynchronous.
     */
    public PluginReloadStartEvent() {
        super(true); // async = true
    }

    /**
     * Returns the handler list for this event type.
     *
     * @return the {@link HandlerList}
     */
    @Override
    public HandlerList getHandlers() { return HANDLERS; }

    /**
     * Static accessor required by Bukkit's event registration.
     *
     * @return the static {@link HandlerList}
     */
    public static HandlerList getHandlerList() { return HANDLERS; }
}
