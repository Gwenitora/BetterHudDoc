package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.trigger.HudTrigger;

import java.util.Collection;
import java.util.function.Function;

/**
 * Registry for named {@link HudTrigger} factories.
 * Triggers are referenced by name in popup YAML configurations.
 */
public interface TriggerManager {

    /**
     * Registers a named trigger factory.
     *
     * @param name the identifier used in YAML configs
     * @param fn   the factory that produces a {@link HudTrigger} from a configuration object
     */
    void addTrigger(String name, Function<Object, HudTrigger<?>> fn);

    /**
     * Returns all registered trigger keys.
     *
     * @return an unmodifiable collection of trigger names
     */
    Collection<String> getAllTriggerKeys();
}
