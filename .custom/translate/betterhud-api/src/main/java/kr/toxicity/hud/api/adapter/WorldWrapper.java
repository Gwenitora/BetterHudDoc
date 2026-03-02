package kr.toxicity.hud.api.adapter;

import java.util.UUID;

/**
 * Platform-agnostic wrapper around a Minecraft world.
 * Avoids direct Bukkit imports in the API and core modules.
 */
public class WorldWrapper {

    /** Human-readable world name (e.g. {@code "world"}). */
    private final String name;

    /** Unique identifier of the world. */
    private final UUID uuid;

    /**
     * Constructs a new wrapper.
     *
     * @param name the world name
     * @param uuid the world UUID
     */
    public WorldWrapper(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Returns the world name.
     *
     * @return world name string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the world's unique identifier.
     *
     * @return world UUID
     */
    public UUID getUuid() {
        return uuid;
    }
}
