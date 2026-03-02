package kr.toxicity.hud.api.scheduler;

/**
 * Platform-agnostic task scheduler for BetterHud.
 * Implementations adapt to Bukkit or Folia schedulers.
 */
public interface HudScheduler {

    /**
     * Schedules {@code runnable} on the main server thread as soon as possible.
     *
     * @param runnable the task to run
     * @return the scheduled {@link HudTask}
     */
    HudTask task(Runnable runnable);

    /**
     * Schedules {@code runnable} asynchronously (off the main thread).
     *
     * @param runnable the task to run
     * @return the scheduled {@link HudTask}
     */
    HudTask asyncTask(Runnable runnable);

    /**
     * Schedules a repeating asynchronous task.
     *
     * @param delay  ticks before the first execution
     * @param period ticks between subsequent executions
     * @param runnable the task to run
     * @return the scheduled {@link HudTask}
     */
    HudTask asyncTaskTimer(long delay, long period, Runnable runnable);

    /**
     * Schedules a delayed synchronous task.
     *
     * @param delay    ticks before execution
     * @param runnable the task to run
     * @return the scheduled {@link HudTask}
     */
    HudTask taskLater(long delay, Runnable runnable);

    /**
     * Schedules a delayed asynchronous task.
     *
     * @param delay    ticks before execution
     * @param runnable the task to run
     * @return the scheduled {@link HudTask}
     */
    HudTask asyncTaskLater(long delay, Runnable runnable);
}
