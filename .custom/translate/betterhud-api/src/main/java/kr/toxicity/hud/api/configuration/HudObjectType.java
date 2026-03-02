package kr.toxicity.hud.api.configuration;

import kr.toxicity.hud.api.compass.Compass;
import kr.toxicity.hud.api.hud.Hud;
import kr.toxicity.hud.api.manager.CompassManager;
import kr.toxicity.hud.api.manager.HudManager;
import kr.toxicity.hud.api.manager.PopupManager;
import kr.toxicity.hud.api.popup.Popup;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Descriptor for a category of {@link HudObject}.
 * Carries the class type, display name, and accessors needed to look up and render objects.
 *
 * @param <T> the concrete {@link HudObject} subtype
 */
public record HudObjectType<T extends HudObject>(
        /** Runtime class of the object type. */
        Class<T> objectClass,
        /** Display name used in configuration and logging. */
        String name,
        /** Function that renders the object for a player (lazy to avoid circular init). */
        Supplier<BiFunction<String, Object, HudComponentSupplier<T>>> renderFunction,
        /** Supplier that lists all loaded object names. */
        Supplier<Collection<String>> namesSupplier,
        /** Function that looks up an object by name. */
        Function<String, T> getter) {

    // -------------------------------------------------------------------------
    // Well-known type constants (lazily initialised to break circular dependency
    // with BetterHudAPI)
    // -------------------------------------------------------------------------

    /**
     * Type descriptor for {@link Hud} objects.
     * Registered managers are accessed via {@link kr.toxicity.hud.api.BetterHudAPI#inst()} lazily.
     */
    public static final HudObjectType<Hud> HUD = new HudObjectType<>(
            Hud.class,
            "hud",
            () -> (name, obj) -> HudComponentSupplier.empty(
                    kr.toxicity.hud.api.BetterHudAPI.inst().getHudManager().getHud(name)),
            () -> kr.toxicity.hud.api.BetterHudAPI.inst().getHudManager().getAllNames(),
            name -> kr.toxicity.hud.api.BetterHudAPI.inst().getHudManager().getHud(name)
    );

    /**
     * Type descriptor for {@link Popup} objects.
     */
    public static final HudObjectType<Popup> POPUP = new HudObjectType<>(
            Popup.class,
            "popup",
            () -> (name, obj) -> HudComponentSupplier.empty(
                    kr.toxicity.hud.api.BetterHudAPI.inst().getPopupManager().getPopup(name)),
            () -> kr.toxicity.hud.api.BetterHudAPI.inst().getPopupManager().getAllNames(),
            name -> kr.toxicity.hud.api.BetterHudAPI.inst().getPopupManager().getPopup(name)
    );

    /**
     * Type descriptor for {@link Compass} objects.
     */
    public static final HudObjectType<Compass> COMPASS = new HudObjectType<>(
            Compass.class,
            "compass",
            () -> (name, obj) -> HudComponentSupplier.empty(
                    kr.toxicity.hud.api.BetterHudAPI.inst().getCompassManager().getCompass(name)),
            () -> kr.toxicity.hud.api.BetterHudAPI.inst().getCompassManager().getAllNames(),
            name -> kr.toxicity.hud.api.BetterHudAPI.inst().getCompassManager().getCompass(name)
    );

    /** Immutable list of all known types. */
    private static final List<HudObjectType<?>> ALL = List.of(HUD, POPUP, COMPASS);

    /**
     * Returns an unmodifiable collection of all registered {@link HudObjectType} descriptors.
     *
     * @return all types
     */
    public static Collection<HudObjectType<?>> types() {
        return ALL;
    }
}
