package kr.toxicity.hud.compass;

import kr.toxicity.hud.api.compass.Compass;
import kr.toxicity.hud.api.component.WidthComponent;
import kr.toxicity.hud.api.configuration.HudObjectType;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.yaml.YamlObject;
import kr.toxicity.hud.yaml.YamlConfigurationImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link Compass}.
 * Loads its configuration from a YAML file and renders directional indicators
 * pointing toward registered {@link kr.toxicity.hud.api.player.PointedLocation} entries.
 */
public class CompassImpl implements Compass {

    /** Unique name of this compass. */
    private final String name;

    /** Whether this compass is active by default. */
    private final boolean isDefault;

    /** Tick interval between direction updates. */
    private final long tick;

    /**
     * Parses a compass from its YAML configuration file.
     *
     * @param name the compass name
     * @param file the configuration file
     * @throws IOException if the file cannot be read
     */
    public CompassImpl(String name, File file) throws IOException {
        this.name = name;
        YamlObject config = YamlConfigurationImpl.load(file);
        this.isDefault = config.getAsBoolean("default", false);
        this.tick = config.getAsLong("tick", 1L);
    }

    /** {@inheritDoc} */
    @Override public String getName() { return name; }

    /** {@inheritDoc} */
    @Override public boolean isDefault() { return isDefault; }

    /** {@inheritDoc} */
    @Override public HudObjectType<?> getType() { return HudObjectType.COMPASS; }

    /** {@inheritDoc} */
    @Override public long tick() { return tick; }

    /**
     * {@inheritDoc}
     * Returns an empty list as a stub implementation.
     * Full implementation would compute bearing and generate indicator components.
     */
    @Override
    public List<WidthComponent> indicate(HudPlayer player) {
        return Collections.emptyList();
    }
}
