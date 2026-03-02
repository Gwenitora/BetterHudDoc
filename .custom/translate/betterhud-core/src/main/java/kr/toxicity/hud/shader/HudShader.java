package kr.toxicity.hud.shader;

/**
 * Immutable descriptor for shader parameters associated with a single HUD element.
 * Implements {@link Comparable} so elements can be depth-sorted by {@link #layer()}.
 *
 * @param guiX     horizontal GUI position as a percentage of the screen width (0–100)
 * @param guiY     vertical GUI position as a percentage of the screen height (0–100)
 * @param scale    render scale factor (1.0 = normal size)
 * @param layer    Z-layer for depth ordering (higher values render on top)
 * @param outline  outline colour in ARGB format; {@code 0} means no outline
 * @param opacity  opacity factor in the range 0.0 (transparent) to 1.0 (opaque)
 * @param property bitfield of enabled {@link ShaderProperty} effects
 */
public record HudShader(
        float guiX,
        float guiY,
        float scale,
        int layer,
        int outline,
        double opacity,
        int property
) implements Comparable<HudShader> {

    /**
     * Sorts shaders by layer ascending (lower layers are rendered first / further back).
     *
     * @param other the other shader to compare with
     * @return comparison result
     */
    @Override
    public int compareTo(HudShader other) {
        return Integer.compare(this.layer, other.layer);
    }

    /**
     * Returns {@code true} if the given {@link ShaderProperty} bit is set.
     *
     * @param prop the property to test
     * @return {@code true} when the property is active
     */
    public boolean hasProperty(ShaderProperty prop) {
        return (property & prop.getBit()) != 0;
    }
}
