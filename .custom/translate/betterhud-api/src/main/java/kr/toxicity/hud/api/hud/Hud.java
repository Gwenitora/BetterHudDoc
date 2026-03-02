package kr.toxicity.hud.api.hud;

import kr.toxicity.hud.api.configuration.HudComponentSupplier;
import kr.toxicity.hud.api.configuration.HudObject;
import kr.toxicity.hud.api.player.HudPlayer;

/**
 * A HUD definition loaded from configuration.
 * Each HUD can create a per-player renderer that evaluates conditions and generates
 * {@link kr.toxicity.hud.api.component.WidthComponent} lists on every tick.
 */
public interface Hud extends HudObject {

    /**
     * Creates a per-player component supplier for this HUD.
     * Called once when a player joins or the plugin reloads.
     *
     * @param player the player to create the renderer for
     * @return a {@link HudComponentSupplier} bound to {@code player}
     */
    HudComponentSupplier<Hud> createRenderer(HudPlayer player);
}
