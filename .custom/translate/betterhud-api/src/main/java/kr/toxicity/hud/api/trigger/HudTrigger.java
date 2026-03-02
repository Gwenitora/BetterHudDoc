package kr.toxicity.hud.api.trigger;

/**
 * Generic trigger that registers an event source with BetterHud.
 * When the registered source fires, BetterHud evaluates listeners and shows popups.
 *
 * @param <T> the event/target type this trigger registers
 */
public interface HudTrigger<T> {

    /**
     * Registers the given target to fire this trigger.
     *
     * @param target the object to attach the trigger to
     */
    void register(T target);
}
