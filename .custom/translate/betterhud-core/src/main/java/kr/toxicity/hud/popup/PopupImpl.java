package kr.toxicity.hud.popup;

import kr.toxicity.hud.api.configuration.HudObjectType;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.plugin.ReloadInfo;
import kr.toxicity.hud.api.popup.Popup;
import kr.toxicity.hud.api.popup.PopupUpdater;
import kr.toxicity.hud.api.update.UpdateEvent;
import kr.toxicity.hud.api.yaml.YamlObject;
import kr.toxicity.hud.yaml.YamlConfigurationImpl;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of {@link Popup}.
 * Loads its definition from a YAML file and manages per-player popup display.
 */
public class PopupImpl implements Popup {

    /** Unique name of this popup. */
    private final String name;

    /** Whether this popup is shown by default. */
    private final boolean isDefault;

    /** Tick interval between updates. */
    private final long tick;

    /** Group name controlling stacking behaviour. */
    private final String groupName;

    /** Maximum simultaneous stack count. */
    private final int maxStack;

    /** Animation frame type identifier. */
    private final String frameType;

    /**
     * Parses a popup from its YAML configuration file.
     *
     * @param name the popup name
     * @param file the configuration file
     * @throws IOException if the file cannot be read
     */
    public PopupImpl(String name, File file) throws IOException {
        this.name = name;
        YamlObject config = YamlConfigurationImpl.load(file);
        this.isDefault = config.getAsBoolean("default", false);
        this.tick = config.getAsLong("tick", 1L);
        this.groupName = config.getAsString("group", name);
        this.maxStack = config.getAsInt("max-stack", 1);
        this.frameType = config.getAsString("frame-type", "LOOP");
    }

    /** {@inheritDoc} */
    @Override public String getName() { return name; }

    /** {@inheritDoc} */
    @Override public boolean isDefault() { return isDefault; }

    /** {@inheritDoc} */
    @Override public HudObjectType<?> getType() { return HudObjectType.POPUP; }

    /** {@inheritDoc} */
    @Override public long tick() { return tick; }

    /** {@inheritDoc} */
    @Override
    public PopupUpdater show(UpdateEvent event, HudPlayer player) {
        // Stub – full implementation would queue the popup for the player's iterator group.
        return null;
    }

    /** {@inheritDoc} */
    @Override public void hide(HudPlayer player) { /* stub */ }

    /** {@inheritDoc} */
    @Override public String getGroupName() { return groupName; }

    /** {@inheritDoc} */
    @Override public int getMaxStack() { return maxStack; }

    /** {@inheritDoc} */
    @Override public String frameType() { return frameType; }
}
