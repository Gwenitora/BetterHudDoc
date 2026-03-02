package kr.toxicity.hud.pack;

/**
 * Defines how the generated resource pack is delivered to players.
 */
public enum PackType {

    /**
     * Serve the pack as a ZIP archive.
     */
    ZIP,

    /**
     * Serve the pack from a flat folder structure (development convenience).
     */
    FOLDER,

    /**
     * Do not generate or serve the pack (dry-run mode).
     */
    NONE
}
