package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.hud.Hud;
import kr.toxicity.hud.api.manager.HudManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.hud.HudImpl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton implementation of {@link HudManager}.
 * Loads HUD definitions from the {@code huds/} sub-folder during reload.
 */
public class HudManagerImpl implements BetterHudManager, HudManager {

    /** Singleton instance. */
    public static final HudManagerImpl INSTANCE = new HudManagerImpl();

    /** Map of HUD name → loaded {@link HudImpl}. */
    private final Map<String, HudImpl> hudMap = new HashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private HudManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** Clears the loaded HUD map before a reload. */
    @Override public void preReload() { hudMap.clear(); }

    /**
     * {@inheritDoc}
     * Scans the {@code huds/} directory and loads each YAML file as a {@link HudImpl}.
     */
    @Override
    public void reload(File workingDir, ReloadInfo info) {
        File hudsDir = new File(workingDir, "huds");
        if (!hudsDir.exists() || !hudsDir.isDirectory()) return;
        File[] files = hudsDir.listFiles(f -> f.getName().endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            String name = file.getName().replace(".yml", "");
            try {
                HudImpl hud = new HudImpl(name, file);
                hudMap.put(name, hud);
            } catch (Exception e) {
                // Log and skip malformed files
            }
        }
    }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { hudMap.clear(); }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Hud"; }

    // -------------------------------------------------------------------------
    // HudManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public Hud getHud(String name) { return hudMap.get(name); }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getAllNames() { return Collections.unmodifiableSet(hudMap.keySet()); }

    /** {@inheritDoc} */
    @Override
    public Collection<Hud> getAllHuds() { return Collections.unmodifiableCollection(hudMap.values()); }
}
