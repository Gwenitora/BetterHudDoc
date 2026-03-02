package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.TriggerManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.api.trigger.HudTrigger;

import java.io.File;
import java.util.*;
import java.util.function.Function;

/**
 * Singleton implementation of {@link TriggerManager}.
 * Stores named trigger factories used by popup YAML configurations.
 */
public class TriggerManagerImpl implements BetterHudManager, TriggerManager {

    /** Singleton instance. */
    public static final TriggerManagerImpl INSTANCE = new TriggerManagerImpl();

    /** Map of trigger name → factory function. */
    private final Map<String, Function<Object, HudTrigger<?>>> triggers = new HashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private TriggerManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void preReload() { /* keep registrations */ }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { triggers.clear(); }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Trigger"; }

    // -------------------------------------------------------------------------
    // TriggerManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public void addTrigger(String name, Function<Object, HudTrigger<?>> fn) {
        triggers.put(Objects.requireNonNull(name), Objects.requireNonNull(fn));
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getAllTriggerKeys() {
        return Collections.unmodifiableSet(triggers.keySet());
    }
}
