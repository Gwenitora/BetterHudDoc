package kr.toxicity.hud;

import kr.toxicity.hud.api.BetterHud;
import kr.toxicity.hud.api.BetterHudAPI;
import kr.toxicity.hud.api.BetterHudBootstrap;
import kr.toxicity.hud.api.manager.*;
import kr.toxicity.hud.api.plugin.ReloadFlagType;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.api.plugin.ReloadState;
import kr.toxicity.hud.manager.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Main implementation of {@link BetterHud}.
 * Orchestrates all managers and the reload lifecycle.
 * Created once by the platform bootstrap during {@code onEnable()}.
 */
public class BetterHudImpl implements BetterHud {

    /** Platform bootstrap providing I/O, logging, scheduling, and NMS. */
    private final BetterHudBootstrap bootstrap;

    /** Ordered list of all managers, initialised in dependency order. */
    private final List<BetterHudManager> managers;

    /** Atomic flag preventing concurrent reloads. */
    private final AtomicBoolean onReload = new AtomicBoolean(false);

    /** Callbacks invoked at the start of every reload. */
    private final List<Runnable> reloadStartTasks = new ArrayList<>();

    /** Callbacks invoked at the end of every reload, receiving the result. */
    private final List<Consumer<ReloadState>> reloadEndTasks = new ArrayList<>();

    /**
     * Constructs the implementation, registers it as the API singleton, and starts all managers.
     *
     * @param bootstrap the platform bootstrap
     */
    public BetterHudImpl(BetterHudBootstrap bootstrap) {
        this.bootstrap = Objects.requireNonNull(bootstrap, "bootstrap must not be null");
        BetterHudAPI.register(this);

        // Initialise managers in dependency order
        managers = List.of(
                ConfigManagerImpl.INSTANCE,
                DatabaseManagerImpl.INSTANCE,
                PlayerManagerImpl.INSTANCE,
                ShaderManagerImpl.INSTANCE,
                TextManagerImpl.INSTANCE,
                PlaceholderManagerImpl.INSTANCE,
                ListenerManagerImpl.INSTANCE,
                TriggerManagerImpl.INSTANCE,
                HudManagerImpl.INSTANCE,
                PopupManagerImpl.INSTANCE,
                CompassManagerImpl.INSTANCE
        );

        managers.forEach(BetterHudManager::start);
    }

    /**
     * {@inheritDoc}
     * Prevents concurrent reloads via an {@link AtomicBoolean} lock.
     */
    @Override
    public ReloadState reload(ReloadFlagType... args) {
        if (!onReload.compareAndSet(false, true)) {
            return ReloadState.OnReload.INSTANCE;
        }
        long start = System.currentTimeMillis();
        try {
            Set<ReloadFlagType> flagSet = args.length > 0
                    ? EnumSet.copyOf(Arrays.asList(args))
                    : Collections.emptySet();
            ReloadInfo info = new ReloadInfo(this, flagSet);

            reloadStartTasks.forEach(Runnable::run);
            File workingDir = bootstrap.dataFolder();

            managers.forEach(BetterHudManager::preReload);
            managers.forEach(m -> m.reload(workingDir, info));
            managers.forEach(BetterHudManager::postReload);

            Map<String, byte[]> pack = kr.toxicity.hud.pack.PackGenerator.generate(info);
            ReloadState state = new ReloadState.Success(pack, System.currentTimeMillis() - start);
            reloadEndTasks.forEach(t -> t.accept(state));
            return state;
        } catch (Exception e) {
            ReloadState state = new ReloadState.Failure(e);
            reloadEndTasks.forEach(t -> t.accept(state));
            return state;
        } finally {
            onReload.set(false);
        }
    }

    /** {@inheritDoc} */
    @Override public boolean isOnReload() { return onReload.get(); }

    /** {@inheritDoc} */
    @Override public BetterHudBootstrap getBootstrap() { return bootstrap; }

    /** {@inheritDoc} */
    @Override public HudManager getHudManager() { return HudManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public PopupManager getPopupManager() { return PopupManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public CompassManager getCompassManager() { return CompassManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public PlayerManager getPlayerManager() { return PlayerManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public ConfigManager getConfigManager() { return ConfigManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public ShaderManager getShaderManager() { return ShaderManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public DatabaseManager getDatabaseManager() { return DatabaseManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public PlaceholderManager getPlaceholderManager() { return PlaceholderManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public ListenerManager getListenerManager() { return ListenerManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public TriggerManager getTriggerManager() { return TriggerManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override public TextManager getTextManager() { return TextManagerImpl.INSTANCE; }

    /** {@inheritDoc} */
    @Override
    public void addReloadStartTask(Runnable task) {
        reloadStartTasks.add(Objects.requireNonNull(task, "task must not be null"));
    }

    /** {@inheritDoc} */
    @Override
    public void addReloadEndTask(Consumer<ReloadState> task) {
        reloadEndTasks.add(Objects.requireNonNull(task, "task must not be null"));
    }
}
