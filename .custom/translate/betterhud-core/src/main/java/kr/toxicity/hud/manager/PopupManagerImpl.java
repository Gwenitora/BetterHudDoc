package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.PopupManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.api.popup.Popup;
import kr.toxicity.hud.popup.PopupImpl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton implementation of {@link PopupManager}.
 * Loads popup definitions from the {@code popups/} sub-folder during reload.
 */
public class PopupManagerImpl implements BetterHudManager, PopupManager {

    /** Singleton instance. */
    public static final PopupManagerImpl INSTANCE = new PopupManagerImpl();

    /** Map of popup name → loaded {@link PopupImpl}. */
    private final Map<String, PopupImpl> popupMap = new HashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private PopupManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** Clears the loaded popup map before a reload. */
    @Override public void preReload() { popupMap.clear(); }

    /**
     * {@inheritDoc}
     * Scans the {@code popups/} directory and loads each YAML file as a {@link PopupImpl}.
     */
    @Override
    public void reload(File workingDir, ReloadInfo info) {
        File dir = new File(workingDir, "popups");
        if (!dir.exists() || !dir.isDirectory()) return;
        File[] files = dir.listFiles(f -> f.getName().endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            String name = file.getName().replace(".yml", "");
            try {
                PopupImpl popup = new PopupImpl(name, file);
                popupMap.put(name, popup);
            } catch (Exception e) {
                // Skip malformed files
            }
        }
    }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { popupMap.clear(); }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Popup"; }

    // -------------------------------------------------------------------------
    // PopupManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public Popup getPopup(String name) { return popupMap.get(name); }

    /** {@inheritDoc} */
    @Override public Collection<String> getAllNames() { return Collections.unmodifiableSet(popupMap.keySet()); }

    /** {@inheritDoc} */
    @Override public Collection<Popup> getAllPopups() { return Collections.unmodifiableCollection(popupMap.values()); }
}
