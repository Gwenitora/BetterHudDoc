package kr.toxicity.hud.bukkit.event;

import kr.toxicity.hud.api.plugin.ReloadState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

/**
 * Fired when a BetterHud reload completes (either successfully or with an error).
 * The reload result is available via {@link #getReloadState()}.
 */
public class PluginReloadedEvent extends Event implements BetterHudEvent {

    /** Required by Bukkit's event system. */
    private static final HandlerList HANDLERS = new HandlerList();

    /** The outcome of the reload that just completed. */
    private final ReloadState reloadState;

    /**
     * Constructs the event with the given reload outcome.
     *
     * @param reloadState the {@link ReloadState} produced by the reload
     */
    public PluginReloadedEvent(ReloadState reloadState) {
        super(true); // async = true
        this.reloadState = Objects.requireNonNull(reloadState, "reloadState must not be null");
    }

    /**
     * Returns the outcome of the reload.
     *
     * @return the {@link ReloadState}
     */
    public ReloadState getReloadState() { return reloadState; }

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
