package kr.toxicity.hud.api.compass;

import kr.toxicity.hud.api.component.WidthComponent;
import kr.toxicity.hud.api.configuration.HudObject;
import kr.toxicity.hud.api.player.HudPlayer;

import java.util.List;

/**
 * A compass definition loaded from configuration.
 * Renders directional indicators pointing toward registered locations.
 */
public interface Compass extends HudObject {

    /**
     * Generates the compass component list for the given player's current heading.
     *
     * @param player the player to render the compass for
     * @return list of {@link WidthComponent} items forming the compass row
     */
    List<WidthComponent> indicate(HudPlayer player);
}
