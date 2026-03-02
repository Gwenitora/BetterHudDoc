package kr.toxicity.hud.bukkit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Fired each time a player's HUD boss bar is updated with new content.
 */
public class HudUpdateEvent extends PlayerEvent implements BetterHudEvent {

    /** Required by Bukkit's event system. */
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs the event for the given player.
     *
     * @param player the player whose HUD was updated
     */
    public HudUpdateEvent(Player player) {
        super(player);
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
