package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.PlaceholderManager;
import kr.toxicity.hud.api.placeholder.HudPlaceholder;
import kr.toxicity.hud.api.placeholder.PlaceholderContainer;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.api.yaml.YamlObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Singleton implementation of {@link PlaceholderManager}.
 * Provides three typed placeholder containers (number, boolean, string).
 */
public class PlaceholderManagerImpl implements BetterHudManager, PlaceholderManager {

    /** Singleton instance. */
    public static final PlaceholderManagerImpl INSTANCE = new PlaceholderManagerImpl();

    /** Container for numeric (Double) placeholders. */
    private final SimpleContainer<Double> numberContainer = new SimpleContainer<>();

    /** Container for boolean placeholders. */
    private final SimpleContainer<Boolean> booleanContainer = new SimpleContainer<>();

    /** Container for string placeholders. */
    private final SimpleContainer<String> stringContainer = new SimpleContainer<>();

    /** Private – use {@link #INSTANCE}. */
    private PlaceholderManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void preReload() { /* keep registrations */ }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() {
        numberContainer.clear();
        booleanContainer.clear();
        stringContainer.clear();
    }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Placeholder"; }

    // -------------------------------------------------------------------------
    // PlaceholderManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public PlaceholderContainer<Double> getNumberContainer() { return numberContainer; }

    /** {@inheritDoc} */
    @Override public PlaceholderContainer<Boolean> getBooleanContainer() { return booleanContainer; }

    /** {@inheritDoc} */
    @Override public PlaceholderContainer<String> getStringContainer() { return stringContainer; }

    // -------------------------------------------------------------------------
    // Inner: simple placeholder container
    // -------------------------------------------------------------------------

    /**
     * A minimal {@link PlaceholderContainer} backed by a {@link HashMap}.
     *
     * @param <T> the placeholder value type
     */
    private static final class SimpleContainer<T> implements PlaceholderContainer<T> {

        /** Placeholder registry. */
        private final Map<String, HudPlaceholder<T>> map = new HashMap<>();

        /** {@inheritDoc} */
        @Override
        public void add(String name, HudPlaceholder<T> placeholder) {
            map.put(Objects.requireNonNull(name), Objects.requireNonNull(placeholder));
        }

        /** {@inheritDoc} */
        @Override
        public HudPlaceholder<T> get(String name, YamlObject config) {
            return map.get(name);
        }

        /**
         * Clears all registered placeholders.
         */
        void clear() { map.clear(); }
    }
}
