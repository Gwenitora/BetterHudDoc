package kr.toxicity.hud.location;

/**
 * A 2D pixel-precise offset used for fine-tuning HUD element positions.
 *
 * @param x horizontal pixel offset (positive = right)
 * @param y vertical pixel offset (positive = down)
 */
public record PixelLocation(int x, int y) {}
