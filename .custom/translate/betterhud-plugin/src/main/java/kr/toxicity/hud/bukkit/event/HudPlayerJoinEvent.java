package kr.toxicity.hud.bukkit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Fired when a player's HUD data is loaded and their {@link kr.toxicity.hud.api.player.HudPlayer}
 * instance is created (i.e. when they join the server).
 */
public class HudPlayerJoinEvent extends PlayerEvent implements BetterHudEvent {

    /** Required by Bukkit's event system. */
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs the event for the given player.
     *
     * @param player the player who joined
     */
    public HudPlayerJoinEvent(Player player) {
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
