package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.ConfigManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import net.kyori.adventure.bossbar.BossBar;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Singleton implementation of {@link ConfigManager}.
 * Reads {@code config.yml} during each reload and exposes its values.
 */
public class ConfigManagerImpl implements BetterHudManager, ConfigManager {

    /** Singleton instance. */
    public static final ConfigManagerImpl INSTANCE = new ConfigManagerImpl();

    /** Number of ticks between HUD updates (default 1). */
    private long tickSpeed = 1L;

    /** Whether debug mode is active. */
    private boolean debug = false;

    /** Current debug verbosity level. */
    private DebugLevel debugLevel = DebugLevel.ASSETS;

    /** Boss-bar color used as the HUD rendering channel. */
    private BossBar.Color barColor = BossBar.Color.YELLOW;

    /** HUD names enabled by default for all players. */
    private List<String> defaultHud = Collections.emptyList();

    /** Number of boss-bar lines reserved for the HUD. */
    private int bossbarLine = 1;

    /** Private – use {@link #INSTANCE}. */
    private ConfigManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void preReload() {
        tickSpeed = 1L;
        debug = false;
        debugLevel = DebugLevel.ASSETS;
        barColor = BossBar.Color.YELLOW;
        defaultHud = Collections.emptyList();
        bossbarLine = 1;
    }

    /**
     * {@inheritDoc}
     * Reads {@code config.yml} from {@code workingDir} and updates all fields.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void reload(File workingDir, ReloadInfo info) {
        File configFile = new File(workingDir, "config.yml");
        if (!configFile.exists()) return;
        try (FileInputStream fis = new FileInputStream(configFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(fis);
            if (map == null) return;
            debug = Boolean.TRUE.equals(map.get("debug"));
            String levelStr = (String) map.getOrDefault("debug-level", "ASSETS");
            try { debugLevel = DebugLevel.valueOf(levelStr.toUpperCase(Locale.ROOT)); }
            catch (IllegalArgumentException ignored) { debugLevel = DebugLevel.ASSETS; }
            Object ts = map.get("tick-speed");
            if (ts instanceof Number n) tickSpeed = n.longValue();
            Object bl = map.get("bossbar-line");
            if (bl instanceof Number n) bossbarLine = n.intValue();
            Object dh = map.get("default-hud");
            if (dh instanceof List<?> list) {
                List<String> names = new ArrayList<>();
                for (Object o : list) if (o != null) names.add(o.toString());
                defaultHud = Collections.unmodifiableList(names);
            }
        } catch (IOException e) {
            // Silently skip – defaults remain
        }
    }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Config"; }

    // -------------------------------------------------------------------------
    // ConfigManager accessors
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public boolean debug() { return debug; }

    /** {@inheritDoc} */
    @Override public int getBossbarLine() { return bossbarLine; }

    /** {@inheritDoc} */
    @Override public DebugLevel getDebugLevel() { return debugLevel; }

    /**
     * Returns the tick interval between HUD updates.
     *
     * @return tick speed
     */
    public long getTickSpeed() { return tickSpeed; }

    /**
     * Returns the boss-bar color used for HUD rendering.
     *
     * @return the {@link BossBar.Color}
     */
    public BossBar.Color getBarColor() { return barColor; }

    /**
     * Returns the list of HUD names active by default for all players.
     *
     * @return default HUD names
     */
    public List<String> getDefaultHud() { return defaultHud; }
}
