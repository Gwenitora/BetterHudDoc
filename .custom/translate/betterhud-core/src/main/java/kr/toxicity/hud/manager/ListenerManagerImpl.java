package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.listener.HudListener;
import kr.toxicity.hud.api.manager.ListenerManager;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.plugin.ReloadInfo;

import java.io.File;
import java.util.*;
import java.util.function.Function;

/**
 * Singleton implementation of {@link ListenerManager}.
 * Stores named listener factories used by HUD/popup YAML configurations.
 */
public class ListenerManagerImpl implements BetterHudManager, ListenerManager {

    /** Singleton instance. */
    public static final ListenerManagerImpl INSTANCE = new ListenerManagerImpl();

    /** Map of listener name → factory function. */
    private final Map<String, Function<HudPlayer, HudListener>> listeners = new HashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private ListenerManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void preReload() { /* keep registrations across reloads */ }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { listeners.clear(); }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Listener"; }

    // -------------------------------------------------------------------------
    // ListenerManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public void addListener(String name, Function<HudPlayer, HudListener> fn) {
        listeners.put(Objects.requireNonNull(name), Objects.requireNonNull(fn));
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getAllListenerKeys() {
        return Collections.unmodifiableSet(listeners.keySet());
    }

    /**
     * Returns the listener factory for {@code name}, or {@code null} if not registered.
     *
     * @param name the listener key
     * @return the factory, or {@code null}
     */
    public Function<HudPlayer, HudListener> getListener(String name) {
        return listeners.get(name);
    }
}
