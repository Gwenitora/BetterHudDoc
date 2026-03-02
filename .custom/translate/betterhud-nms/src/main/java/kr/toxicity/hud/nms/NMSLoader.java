package kr.toxicity.hud.nms;

import kr.toxicity.hud.api.version.MinecraftVersion;
import org.bukkit.Bukkit;

/**
 * Detects the running server's Minecraft version and instantiates the correct {@link NMSHandler}.
 *
 * <p>Version detection is performed by inspecting {@link Bukkit#getBukkitVersion()}, which returns
 * a string like {@code "1.21.4-R0.1-SNAPSHOT"}.  The leading numeric part is parsed into a
 * {@link MinecraftVersion} and mapped to the appropriate {@link NMSVersion}.</p>
 */
public final class NMSLoader {

    /** Prevent instantiation – static factory class. */
    private NMSLoader() {}

    /**
     * Detects the server version and returns an initialised {@link NMSHandler}.
     *
     * @return the version-appropriate {@link NMSHandler}
     * @throws IllegalStateException        if the Minecraft version is not supported
     * @throws ReflectiveOperationException if the NMS handler cannot be initialised
     */
    public static NMSHandler load() throws ReflectiveOperationException {
        String raw = detectVersionString();
        MinecraftVersion mcVersion = MinecraftVersion.parse(raw);
        NMSVersion nmsVersion = mapToNMSVersion(mcVersion);
        return new ReflectionNMSHandler(nmsVersion);
    }

    /**
     * Extracts the numeric version string from Bukkit's version declaration.
     *
     * @return a string like {@code "1.21.4"}
     */
    private static String detectVersionString() {
        String bukkitVersion = Bukkit.getBukkitVersion(); // e.g. "1.21.4-R0.1-SNAPSHOT"
        // Strip the "-R..." Maven qualifier
        int dash = bukkitVersion.indexOf('-');
        return dash > 0 ? bukkitVersion.substring(0, dash) : bukkitVersion;
    }

    /**
     * Maps a parsed {@link MinecraftVersion} to the closest matching {@link NMSVersion}.
     *
     * @param mc the detected Minecraft version
     * @return the matching {@link NMSVersion}
     * @throws IllegalStateException if the version is below the minimum supported version
     */
    private static NMSVersion mapToNMSVersion(MinecraftVersion mc) {
        if (mc.compareTo(MinecraftVersion.V1_21_11) >= 0) return NMSVersion.V1_21_R7;
        if (mc.compareTo(MinecraftVersion.V1_21_9)  >= 0) return NMSVersion.V1_21_R6;
        if (mc.compareTo(MinecraftVersion.V1_21_6)  >= 0) return NMSVersion.V1_21_R5;
        if (mc.compareTo(MinecraftVersion.V1_21_5)  >= 0) return NMSVersion.V1_21_R4;
        if (mc.compareTo(MinecraftVersion.V1_21_4)  >= 0) return NMSVersion.V1_21_R3;
        if (mc.compareTo(MinecraftVersion.V1_21_2)  >= 0) return NMSVersion.V1_21_R2;
        if (mc.compareTo(MinecraftVersion.V1_21_0)  >= 0) return NMSVersion.V1_21_R1;
        if (mc.compareTo(MinecraftVersion.V1_20_5)  >= 0) return NMSVersion.V1_20_R4;
        if (mc.compareTo(MinecraftVersion.V1_20_3)  >= 0) return NMSVersion.V1_20_R3;
        throw new IllegalStateException("Unsupported Minecraft version: " + mc
                + ". BetterHud requires 1.20.3 or newer.");
    }
}
