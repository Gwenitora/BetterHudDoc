package kr.toxicity.hud.image;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Holds a loaded image together with its pixel dimensions.
 * Immutable after construction.
 */
public class LoadedImage {

    /** The decoded image data. */
    private final BufferedImage image;

    /** Pixel width of the image. */
    private final int width;

    /** Pixel height of the image. */
    private final int height;

    /**
     * Constructs a {@link LoadedImage} from the given buffered image.
     * Width and height are read from the image itself.
     *
     * @param image the decoded image
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public LoadedImage(BufferedImage image) {
        this.image = Objects.requireNonNull(image, "image must not be null");
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Returns the decoded image data.
     *
     * @return the {@link BufferedImage}
     */
    public BufferedImage getImage() { return image; }

    /**
     * Returns the pixel width of the image.
     *
     * @return image width in pixels
     */
    public int getWidth() { return width; }

    /**
     * Returns the pixel height of the image.
     *
     * @return image height in pixels
     */
    public int getHeight() { return height; }
}
