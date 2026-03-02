package kr.toxicity.hud.pack;

import kr.toxicity.hud.api.version.MinecraftVersion;

import java.nio.charset.StandardCharsets;

/**
 * Generates the {@code pack.mcmeta} JSON file required by Minecraft resource packs.
 * The {@code pack_format} integer changes with each Minecraft version.
 */
public final class PackMeta {

    /** Prevent instantiation – static utility class. */
    private PackMeta() {}

    /**
     * Returns the {@code pack.mcmeta} bytes for the given Minecraft version.
     *
     * @param version the target {@link MinecraftVersion}
     * @return UTF-8 encoded JSON bytes
     */
    public static byte[] generate(MinecraftVersion version) {
        int format = resolvePackFormat(version);
        String json = """
                {
                  "pack": {
                    "pack_format": %d,
                    "description": "BetterHud resource pack"
                  }
                }
                """.formatted(format);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Maps a {@link MinecraftVersion} to the numeric resource-pack format version.
     *
     * @param version the Minecraft version
     * @return the pack_format integer
     */
    private static int resolvePackFormat(MinecraftVersion version) {
        if (version.compareTo(MinecraftVersion.V1_21_4) >= 0) return 46;
        if (version.compareTo(MinecraftVersion.V1_21_2) >= 0) return 42;
        if (version.compareTo(MinecraftVersion.V1_21_0) >= 0) return 34;
        if (version.compareTo(MinecraftVersion.V1_20_5) >= 0) return 32;
        if (version.compareTo(MinecraftVersion.V1_20_3) >= 0) return 22;
        return 18; // fallback for older versions
    }
}
