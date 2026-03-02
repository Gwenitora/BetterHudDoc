package kr.toxicity.hud.api.adapter;

/**
 * Platform-agnostic wrapper around a world location.
 * Avoids direct Bukkit imports in the API and core modules.
 */
public class LocationWrapper {

    /** X coordinate. */
    private final double x;

    /** Y coordinate. */
    private final double y;

    /** Z coordinate. */
    private final double z;

    /** Yaw rotation in degrees. */
    private final float yaw;

    /** Pitch rotation in degrees. */
    private final float pitch;

    /** The world this location belongs to. */
    private final WorldWrapper world;

    /**
     * Constructs a new location wrapper.
     *
     * @param x     the X coordinate
     * @param y     the Y coordinate
     * @param z     the Z coordinate
     * @param yaw   the yaw rotation
     * @param pitch the pitch rotation
     * @param world the enclosing world
     */
    public LocationWrapper(double x, double y, double z, float yaw, float pitch, WorldWrapper world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    /** @return the X coordinate */
    public double getX() { return x; }

    /** @return the Y coordinate */
    public double getY() { return y; }

    /** @return the Z coordinate */
    public double getZ() { return z; }

    /** @return the yaw rotation in degrees */
    public float getYaw() { return yaw; }

    /** @return the pitch rotation in degrees */
    public float getPitch() { return pitch; }

    /**
     * Returns the world this location is in.
     *
     * @return the {@link WorldWrapper}
     */
    public WorldWrapper getWorld() { return world; }
}
