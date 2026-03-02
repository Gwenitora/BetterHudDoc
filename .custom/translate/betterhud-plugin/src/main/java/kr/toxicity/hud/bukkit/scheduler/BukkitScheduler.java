package kr.toxicity.hud.bukkit.scheduler;

import kr.toxicity.hud.api.scheduler.HudScheduler;
import kr.toxicity.hud.api.scheduler.HudTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * {@link HudScheduler} implementation backed by Bukkit's built-in task scheduler.
 * Suitable for Spigot and Paper servers (not Folia – use a Folia-aware scheduler there).
 */
public class BukkitScheduler implements HudScheduler {

    /** Reference to the owning plugin, required for all scheduler calls. */
    private final JavaPlugin plugin;

    /**
     * Constructs the scheduler for the given plugin.
     *
     * @param plugin the owning {@link JavaPlugin}
     */
    public BukkitScheduler(JavaPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null");
    }

    /**
     * {@inheritDoc}
     * Schedules {@code runnable} on the main server thread as soon as possible.
     */
    @Override
    public HudTask task(Runnable runnable) {
        return new BukkitTaskWrapper(
                plugin.getServer().getScheduler().runTask(plugin, runnable));
    }

    /**
     * {@inheritDoc}
     * Schedules {@code runnable} on an async worker thread.
     */
    @Override
    public HudTask asyncTask(Runnable runnable) {
        return new BukkitTaskWrapper(
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable));
    }

    /**
     * {@inheritDoc}
     * Schedules a repeating async task with the given delay and period.
     */
    @Override
    public HudTask asyncTaskTimer(long delay, long period, Runnable runnable) {
        return new BukkitTaskWrapper(
                plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period));
    }

    /**
     * {@inheritDoc}
     * Schedules a delayed sync task.
     */
    @Override
    public HudTask taskLater(long delay, Runnable runnable) {
        return new BukkitTaskWrapper(
                plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay));
    }

    /**
     * {@inheritDoc}
     * Schedules a delayed async task.
     */
    @Override
    public HudTask asyncTaskLater(long delay, Runnable runnable) {
        return new BukkitTaskWrapper(
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay));
    }
}
