package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.compass.Compass;
import kr.toxicity.hud.api.manager.CompassManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.compass.CompassImpl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton implementation of {@link CompassManager}.
 * Loads compass definitions from the {@code compasses/} sub-folder during reload.
 */
public class CompassManagerImpl implements BetterHudManager, CompassManager {

    /** Singleton instance. */
    public static final CompassManagerImpl INSTANCE = new CompassManagerImpl();

    /** Map of compass name → loaded {@link CompassImpl}. */
    private final Map<String, CompassImpl> compassMap = new HashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private CompassManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** Clears the compass map before reload. */
    @Override public void preReload() { compassMap.clear(); }

    /**
     * {@inheritDoc}
     * Scans the {@code compasses/} directory and loads each YAML file as a {@link CompassImpl}.
     */
    @Override
    public void reload(File workingDir, ReloadInfo info) {
        File dir = new File(workingDir, "compasses");
        if (!dir.exists() || !dir.isDirectory()) return;
        File[] files = dir.listFiles(f -> f.getName().endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            String name = file.getName().replace(".yml", "");
            try {
                CompassImpl compass = new CompassImpl(name, file);
                compassMap.put(name, compass);
            } catch (Exception e) {
                // Skip malformed files
            }
        }
    }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { compassMap.clear(); }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Compass"; }

    // -------------------------------------------------------------------------
    // CompassManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public Compass getCompass(String name) { return compassMap.get(name); }

    /** {@inheritDoc} */
    @Override public Collection<String> getAllNames() { return Collections.unmodifiableSet(compassMap.keySet()); }

    /** {@inheritDoc} */
    @Override public Collection<Compass> getAllCompasses() { return Collections.unmodifiableCollection(compassMap.values()); }
}
