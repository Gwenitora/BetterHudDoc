package kr.toxicity.hud.api.version;

import java.util.Objects;

/**
 * Represents a specific Minecraft release as a three-component version number.
 * Implements {@link Comparable} so versions can be compared for range checks.
 *
 * <p>Example: {@code MinecraftVersion.V1_21_4} represents Minecraft 1.21.4.</p>
 */
public record MinecraftVersion(int first, int second, int third) implements Comparable<MinecraftVersion> {

    // -------------------------------------------------------------------------
    // Well-known version constants
    // -------------------------------------------------------------------------

    /** Minecraft 1.20.3 */
    public static final MinecraftVersion V1_20_3 = new MinecraftVersion(1, 20, 3);
    /** Minecraft 1.20.4 */
    public static final MinecraftVersion V1_20_4 = new MinecraftVersion(1, 20, 4);
    /** Minecraft 1.20.5 */
    public static final MinecraftVersion V1_20_5 = new MinecraftVersion(1, 20, 5);
    /** Minecraft 1.20.6 */
    public static final MinecraftVersion V1_20_6 = new MinecraftVersion(1, 20, 6);
    /** Minecraft 1.21.0 */
    public static final MinecraftVersion V1_21_0 = new MinecraftVersion(1, 21, 0);
    /** Minecraft 1.21.1 */
    public static final MinecraftVersion V1_21_1 = new MinecraftVersion(1, 21, 1);
    /** Minecraft 1.21.2 */
    public static final MinecraftVersion V1_21_2 = new MinecraftVersion(1, 21, 2);
    /** Minecraft 1.21.3 */
    public static final MinecraftVersion V1_21_3 = new MinecraftVersion(1, 21, 3);
    /** Minecraft 1.21.4 */
    public static final MinecraftVersion V1_21_4 = new MinecraftVersion(1, 21, 4);
    /** Minecraft 1.21.5 */
    public static final MinecraftVersion V1_21_5 = new MinecraftVersion(1, 21, 5);
    /** Minecraft 1.21.6 */
    public static final MinecraftVersion V1_21_6 = new MinecraftVersion(1, 21, 6);
    /** Minecraft 1.21.7 */
    public static final MinecraftVersion V1_21_7 = new MinecraftVersion(1, 21, 7);
    /** Minecraft 1.21.8 */
    public static final MinecraftVersion V1_21_8 = new MinecraftVersion(1, 21, 8);
    /** Minecraft 1.21.9 */
    public static final MinecraftVersion V1_21_9 = new MinecraftVersion(1, 21, 9);
    /** Minecraft 1.21.10 */
    public static final MinecraftVersion V1_21_10 = new MinecraftVersion(1, 21, 10);
    /** Minecraft 1.21.11 */
    public static final MinecraftVersion V1_21_11 = new MinecraftVersion(1, 21, 11);

    // -------------------------------------------------------------------------
    // Parsing
    // -------------------------------------------------------------------------

    /**
     * Parses a version string of the form {@code "1.21.4"} or {@code "1.21"}.
     *
     * @param raw the raw version string
     * @return the parsed {@link MinecraftVersion}
     * @throws IllegalArgumentException if the string cannot be parsed
     * @throws NullPointerException     if {@code raw} is {@code null}
     */
    public static MinecraftVersion parse(String raw) {
        Objects.requireNonNull(raw, "version string must not be null");
        String[] parts = raw.trim().split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Cannot parse Minecraft version: " + raw);
        }
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);
        int c = parts.length >= 3 ? Integer.parseInt(parts[2]) : 0;
        return new MinecraftVersion(a, b, c);
    }

    // -------------------------------------------------------------------------
    // Comparable
    // -------------------------------------------------------------------------

    /**
     * Compares this version to {@code other} numerically,
     * component by component (first → second → third).
     *
     * @param other the other version to compare with
     * @return a negative integer, zero, or positive integer
     */
    @Override
    public int compareTo(MinecraftVersion other) {
        int cmp = Integer.compare(this.first, other.first);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(this.second, other.second);
        if (cmp != 0) return cmp;
        return Integer.compare(this.third, other.third);
    }

    /**
     * Returns the version as a human-readable string, e.g. {@code "1.21.4"}.
     *
     * @return dot-separated version string
     */
    @Override
    public String toString() {
        return first + "." + second + "." + third;
    }
}
