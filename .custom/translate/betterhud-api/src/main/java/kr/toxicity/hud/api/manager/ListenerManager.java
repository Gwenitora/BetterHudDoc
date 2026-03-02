package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.listener.HudListener;
import kr.toxicity.hud.api.player.HudPlayer;
import kr.toxicity.hud.api.update.UpdateEvent;

import java.util.Collection;
import java.util.function.Function;

/**
 * Registry for named {@link HudListener} factories.
 * Listeners are referenced by name in HUD/popup YAML configurations.
 */
public interface ListenerManager {

    /**
     * Registers a named listener factory.
     *
     * @param name the identifier used in YAML configs
     * @param fn   the factory that produces a {@link HudListener} from a player
     */
    void addListener(String name, Function<HudPlayer, HudListener> fn);

    /**
     * Returns all registered listener keys.
     *
     * @return an unmodifiable collection of listener names
     */
    Collection<String> getAllListenerKeys();
}
