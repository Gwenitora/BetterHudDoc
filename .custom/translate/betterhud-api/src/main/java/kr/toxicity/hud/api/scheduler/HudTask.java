package kr.toxicity.hud.api.scheduler;

/**
 * Abstraction over a scheduled task returned by {@link HudScheduler}.
 * Allows callers to cancel the task without depending on platform types.
 */
public interface HudTask {

    /**
     * Cancels this task.
     * Calling this method on an already-cancelled task has no effect.
     */
    void cancel();

    /**
     * Returns {@code true} if this task has been cancelled.
     *
     * @return {@code true} when cancelled
     */
    boolean isCancelled();
}
