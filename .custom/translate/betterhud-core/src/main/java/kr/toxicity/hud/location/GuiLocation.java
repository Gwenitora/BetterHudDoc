package kr.toxicity.hud.location;

/**
 * Represents a 2D position on the player's GUI screen expressed as fractional percentages.
 * Both coordinates are in the range 0.0 (left/top) to 1.0 (right/bottom).
 *
 * @param x horizontal screen fraction (0.0–1.0)
 * @param y vertical screen fraction (0.0–1.0)
 */
public record GuiLocation(float x, float y) {

    /**
     * Compact constructor that validates the coordinate range.
     *
     * @param x horizontal fraction
     * @param y vertical fraction
     * @throws IllegalArgumentException if either coordinate is outside [0, 100]
     */
    public GuiLocation {
        if (x < 0f || x > 100f) throw new IllegalArgumentException("GUI x out of range: " + x);
        if (y < 0f || y > 100f) throw new IllegalArgumentException("GUI y out of range: " + y);
    }
}
