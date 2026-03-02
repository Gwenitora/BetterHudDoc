package kr.toxicity.hud.api.component;

import net.kyori.adventure.text.TextComponent;

import java.util.Objects;

/**
 * Pairs an Adventure {@link TextComponent.Builder} with the pixel width it occupies on screen.
 * Used to accumulate HUD text elements while tracking their layout width.
 */
public record WidthComponent(TextComponent.Builder component, int width) {

    /**
     * Returns a new {@link WidthComponent} combining {@code this} and {@code other}.
     * The components are appended, widths are summed.
     *
     * @param other the component to append
     * @return a new combined {@link WidthComponent}
     */
    public WidthComponent plus(WidthComponent other) {
        Objects.requireNonNull(other, "other must not be null");
        TextComponent.Builder combined = net.kyori.adventure.text.Component.text();
        combined.append(this.component.build());
        combined.append(other.component.build());
        return new WidthComponent(combined, this.width + other.width);
    }

    /**
     * Returns a deep copy of this component.
     *
     * @return a new {@link WidthComponent} with the same content and width
     */
    public WidthComponent copy() {
        TextComponent.Builder copy = net.kyori.adventure.text.Component.text();
        copy.append(this.component.build());
        return new WidthComponent(copy, this.width);
    }
}
