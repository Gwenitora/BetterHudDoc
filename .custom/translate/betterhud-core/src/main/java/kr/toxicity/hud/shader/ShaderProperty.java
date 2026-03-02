package kr.toxicity.hud.shader;

/**
 * Bitfield properties that can be combined to enable visual effects in the HUD shader.
 * Each constant represents one bit in {@link HudShader#property()}.
 */
public enum ShaderProperty {

    /** Oscillating wave distortion effect. */
    WAVE(1),

    /** Full-colour rainbow cycling effect. */
    RAINBOW(2),

    /** Subtle pastel rainbow effect. */
    TINY_RAINBOW(4);

    /** The bit value of this property in the bitfield. */
    private final int bit;

    /**
     * Constructs a property with the given bit mask.
     *
     * @param bit the bit mask value
     */
    ShaderProperty(int bit) {
        this.bit = bit;
    }

    /**
     * Returns the bit mask value for this property.
     *
     * @return the bit mask
     */
    public int getBit() {
        return bit;
    }
}
