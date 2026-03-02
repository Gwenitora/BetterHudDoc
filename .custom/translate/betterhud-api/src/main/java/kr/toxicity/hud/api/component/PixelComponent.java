package kr.toxicity.hud.api.component;

import java.util.Objects;

/**
 * Combines a {@link WidthComponent} with the pixel offset at which it should be rendered.
 * Used when assembling layered or offset HUD elements.
 */
public record PixelComponent(WidthComponent component, int pixel) {

    /**
     * Returns a new {@link PixelComponent} that appends {@code other}.
     * The widths are summed; the pixel offset of {@code this} is preserved.
     *
     * @param other the component to append
     * @return the merged {@link PixelComponent}
     */
    public PixelComponent plus(PixelComponent other) {
        Objects.requireNonNull(other, "other must not be null");
        return new PixelComponent(component.plus(other.component()), pixel + other.pixel());
    }

    /**
     * Returns a deep copy of this component.
     *
     * @return a new {@link PixelComponent} with copied content
     */
    public PixelComponent copy() {
        return new PixelComponent(component.copy(), pixel);
    }
}
