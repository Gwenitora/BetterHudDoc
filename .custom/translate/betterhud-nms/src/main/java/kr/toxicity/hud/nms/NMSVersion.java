package kr.toxicity.hud.nms;

/**
 * Identifies a specific Minecraft/CraftBukkit NMS revision.
 * Each enum constant covers one or more Minecraft releases that share the same
 * obfuscation mapping and NMS package.
 */
public enum NMSVersion {

    /** 1.20.3 – 1.20.4 */
    V1_20_R3(20, 3, 22),

    /** 1.20.5 – 1.20.6 */
    V1_20_R4(20, 4, 32),

    /** 1.21.0 – 1.21.1 */
    V1_21_R1(21, 1, 34),

    /** 1.21.2 – 1.21.3 */
    V1_21_R2(21, 2, 42),

    /** 1.21.4 */
    V1_21_R3(21, 3, 46),

    /** 1.21.5 */
    V1_21_R4(21, 4, 55),

    /** 1.21.6 – 1.21.8 */
    V1_21_R5(21, 5, 64),

    /** 1.21.9 – 1.21.10 */
    V1_21_R6(21, 6, 69),

    /** 1.21.11 */
    V1_21_R7(21, 7, 75);

    /** Major MC version component (e.g. {@code 21} for 1.21.x). */
    private final int version;

    /** Minor NMS revision number within the major version. */
    private final int subVersion;

    /** Resource-pack {@code pack_format} version for this NMS revision. */
    private final int metaVersion;

    /**
     * Constructs an NMS version constant.
     *
     * @param version     major version component
     * @param subVersion  minor NMS revision
     * @param metaVersion resource-pack format version
     */
    NMSVersion(int version, int subVersion, int metaVersion) {
        this.version = version;
        this.subVersion = subVersion;
        this.metaVersion = metaVersion;
    }

    /**
     * Returns the major MC version component.
     *
     * @return the major version integer
     */
    public int getVersion() { return version; }

    /**
     * Returns the minor NMS revision number.
     *
     * @return the sub-version integer
     */
    public int getSubVersion() { return subVersion; }

    /**
     * Returns the resource-pack {@code pack_format} value for this NMS revision.
     *
     * @return the meta version integer
     */
    public int getMetaVersion() { return metaVersion; }
}
