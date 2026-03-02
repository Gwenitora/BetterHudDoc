package kr.toxicity.hud.api.configuration;

import kr.toxicity.hud.api.component.WidthComponent;
import kr.toxicity.hud.api.player.HudPlayer;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * A per-player supplier of {@link WidthComponent} lists for a specific {@link HudObject}.
 * Created fresh for each player during reload so player-specific state is isolated.
 *
 * @param <T> the {@link HudObject} type this supplier belongs to
 */
public interface HudComponentSupplier<T extends HudObject> extends Supplier<List<WidthComponent>> {

    /**
     * Returns the parent {@link HudObject} that produced this supplier.
     *
     * @return the owning {@link HudObject}
     */
    T parent();

    /**
     * Returns an empty supplier that always returns an empty list.
     *
     * @param parent the nominal parent object
     * @param <T>    the object type
     * @return an always-empty {@link HudComponentSupplier}
     */
    static <T extends HudObject> HudComponentSupplier<T> empty(T parent) {
        return new HudComponentSupplier<>() {
            @Override public T parent() { return parent; }
            @Override public List<WidthComponent> get() { return Collections.emptyList(); }
        };
    }

    /**
     * Creates a supplier backed by a fixed list.
     *
     * @param parent     the nominal parent object
     * @param components the fixed list to return
     * @param <T>        the object type
     * @return a fixed {@link HudComponentSupplier}
     */
    static <T extends HudObject> HudComponentSupplier<T> of(T parent, List<WidthComponent> components) {
        return new HudComponentSupplier<>() {
            @Override public T parent() { return parent; }
            @Override public List<WidthComponent> get() { return components; }
        };
    }
}
