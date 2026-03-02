package kr.toxicity.hud.hud;

import kr.toxicity.hud.api.configuration.HudComponentSupplier;
import kr.toxicity.hud.api.configuration.HudObjectType;
import kr.toxicity.hud.api.hud.Hud;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.layout.HudLayout;
import kr.toxicity.hud.yaml.YamlConfigurationImpl;
import kr.toxicity.hud.api.yaml.YamlObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link Hud}.
 * Loads layouts from a YAML definition file and renders them per player.
 */
public class HudImpl implements Hud {

    /** Unique name of this HUD as defined by its configuration file name. */
    private final String name;

    /** Whether this HUD is active for all players by default. */
    private final boolean isDefault;

    /** Update tick interval – how many server ticks between re-renders. */
    private final long tick;

    /** Ordered list of layout elements that make up this HUD. */
    private final List<HudLayout> layouts;

    /**
     * Parses a HUD from its YAML configuration file.
     *
     * @param name the HUD name (derived from file name, without {@code .yml})
     * @param file the YAML configuration file
     * @throws IOException if the file cannot be read
     */
    public HudImpl(String name, File file) throws IOException {
        this.name = name;
        YamlObject config = YamlConfigurationImpl.load(file);
        this.isDefault = config.getAsBoolean("default", false);
        this.tick = config.getAsLong("tick", 1L);

        List<HudLayout> layoutList = new ArrayList<>();
        YamlObject layoutSection = config.get("layout") != null
                ? config.get("layout").asObject() : null;
        if (layoutSection != null) {
            layoutSection.forEach(entry -> {
                YamlObject node = entry.getValue().asObject();
                if (node != null) layoutList.add(HudLayout.fromYaml(node));
            });
        }
        this.layouts = Collections.unmodifiableList(layoutList);
    }

    // -------------------------------------------------------------------------
    // HudObject
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public String getName() { return name; }

    /** {@inheritDoc} */
    @Override public boolean isDefault() { return isDefault; }

    /** {@inheritDoc} */
    @Override public HudObjectType<?> getType() { return HudObjectType.HUD; }

    /** {@inheritDoc} */
    @Override public long tick() { return tick; }

    // -------------------------------------------------------------------------
    // Hud
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * Returns a supplier that provides an empty component list as a stub implementation.
     * Full rendering logic would evaluate conditions and build {@link kr.toxicity.hud.api.component.WidthComponent} lists.
     */
    @Override
    public HudComponentSupplier<Hud> createRenderer(HudPlayer player) {
        return HudComponentSupplier.empty(this);
    }

    /**
     * Returns the list of layout elements for this HUD.
     *
     * @return unmodifiable list of {@link HudLayout}
     */
    public List<HudLayout> getLayouts() { return layouts; }
}
