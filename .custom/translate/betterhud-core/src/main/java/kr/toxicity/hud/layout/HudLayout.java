package kr.toxicity.hud.layout;

import kr.toxicity.hud.api.yaml.YamlObject;

import java.util.Locale;

/**
 * Describes the positioning and rendering options of one element within a HUD.
 * Loaded from YAML during reload via {@link #fromYaml(YamlObject)}.
 */
public class HudLayout {

    /** Horizontal GUI position as a percentage of screen width (0–100). */
    private final float guiX;

    /** Vertical GUI position as a percentage of screen height (0–100). */
    private final float guiY;

    /** Horizontal pixel offset applied after the GUI position. */
    private final int pixelX;

    /** Vertical pixel offset applied after the GUI position. */
    private final int pixelY;

    /** Horizontal alignment of the rendered content. */
    private final LayoutAlign align;

    /** Opacity factor for this element (0.0 transparent – 1.0 opaque). */
    private final double opacity;

    /** Z-layer controlling depth rendering order. */
    private final int layer;

    /**
     * Constructs a layout with all fields explicit.
     *
     * @param guiX    horizontal GUI position percentage
     * @param guiY    vertical GUI position percentage
     * @param pixelX  horizontal pixel offset
     * @param pixelY  vertical pixel offset
     * @param align   text/image alignment
     * @param opacity opacity factor
     * @param layer   Z-layer depth
     */
    public HudLayout(float guiX, float guiY, int pixelX, int pixelY,
                     LayoutAlign align, double opacity, int layer) {
        this.guiX = guiX;
        this.guiY = guiY;
        this.pixelX = pixelX;
        this.pixelY = pixelY;
        this.align = align;
        this.opacity = opacity;
        this.layer = layer;
    }

    /**
     * Parses a {@link HudLayout} from a YAML configuration node.
     *
     * @param yaml the YAML object to read
     * @return the parsed {@link HudLayout}
     */
    public static HudLayout fromYaml(YamlObject yaml) {
        float guiX = yaml.getAsFloat("gui-x", 50f);
        float guiY = yaml.getAsFloat("gui-y", 50f);
        int pixelX = yaml.getAsInt("pixel-x", 0);
        int pixelY = yaml.getAsInt("pixel-y", 0);
        String alignStr = yaml.getAsString("align", "CENTER").toUpperCase(Locale.ROOT);
        LayoutAlign align;
        try { align = LayoutAlign.valueOf(alignStr); }
        catch (IllegalArgumentException e) { align = LayoutAlign.CENTER; }
        double opacity = yaml.getAsDouble("opacity", 1.0);
        int layer = yaml.getAsInt("layer", 0);
        return new HudLayout(guiX, guiY, pixelX, pixelY, align, opacity, layer);
    }

    /** @return horizontal GUI position percentage */
    public float getGuiX() { return guiX; }

    /** @return vertical GUI position percentage */
    public float getGuiY() { return guiY; }

    /** @return horizontal pixel offset */
    public int getPixelX() { return pixelX; }

    /** @return vertical pixel offset */
    public int getPixelY() { return pixelY; }

    /**
     * Returns the horizontal alignment for this layout element.
     *
     * @return the {@link LayoutAlign}
     */
    public LayoutAlign getAlign() { return align; }

    /** @return opacity factor (0.0–1.0) */
    public double getOpacity() { return opacity; }

    /** @return Z-layer depth */
    public int getLayer() { return layer; }
}
