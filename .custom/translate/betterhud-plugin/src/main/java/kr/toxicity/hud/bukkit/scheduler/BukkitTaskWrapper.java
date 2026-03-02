package kr.toxicity.hud.bukkit.scheduler;

import kr.toxicity.hud.api.scheduler.HudTask;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

/**
 * Adapts a Bukkit {@link BukkitTask} to BetterHud's {@link HudTask} interface.
 */
public class BukkitTaskWrapper implements HudTask {

    /** The underlying Bukkit task. */
    private final BukkitTask delegate;

    /**
     * Wraps the given Bukkit task.
     *
     * @param delegate the Bukkit task to wrap
     */
    public BukkitTaskWrapper(BukkitTask delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
    }

    /** {@inheritDoc} */
    @Override
    public void cancel() {
        delegate.cancel();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCancelled() {
        return delegate.isCancelled();
    }
}
