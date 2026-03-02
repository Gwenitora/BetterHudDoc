package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.ShaderManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import net.kyori.adventure.bossbar.BossBar;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton implementation of {@link ShaderManager}.
 * Generates GLSL shader files that are embedded in the resource pack.
 */
public class ShaderManagerImpl implements BetterHudManager, ShaderManager {

    /** Singleton instance. */
    public static final ShaderManagerImpl INSTANCE = new ShaderManagerImpl();

    /** Boss-bar color whose rendering is replaced by the HUD shader. */
    private BossBar.Color barColor = BossBar.Color.YELLOW;

    /** Whether the vanilla XP level text is hidden. */
    private boolean disableLevelText = false;

    /** GLSL {@code #define} constants injected at pack generation time. */
    private final Map<String, String> constants = new LinkedHashMap<>();

    /** Registered tag suppliers per shader type. */
    private final Map<ShaderType, List<ShaderTagSupplier>> tagSuppliers = new LinkedHashMap<>();

    /** Private – use {@link #INSTANCE}. */
    private ShaderManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** Resets mutable shader state before reload. */
    @Override
    public void preReload() {
        barColor = BossBar.Color.YELLOW;
        disableLevelText = false;
        constants.clear();
        tagSuppliers.clear();
    }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) { /* nothing – uses ConfigManager values */ }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { constants.clear(); tagSuppliers.clear(); }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Shader"; }

    // -------------------------------------------------------------------------
    // ShaderManager
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public void addConstant(String key, String value) {
        constants.put(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public void addTagSupplier(ShaderType type, ShaderTagSupplier supplier) {
        tagSuppliers.computeIfAbsent(type, t -> new ArrayList<>()).add(supplier);
    }

    /**
     * Returns the current boss-bar color being used by the shader.
     *
     * @return the {@link BossBar.Color}
     */
    public BossBar.Color getBarColor() { return barColor; }

    /**
     * Returns the map of GLSL {@code #define} constants.
     *
     * @return constants map
     */
    public Map<String, String> getConstants() { return constants; }

    /**
     * Returns whether the vanilla XP level text is disabled.
     *
     * @return {@code true} when disabled
     */
    public boolean isDisableLevelText() { return disableLevelText; }
}
