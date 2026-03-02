package kr.toxicity.hud.api.player;

/**
 * Provides access to a player's skin texture data.
 * Used when rendering player head images inside HUD elements.
 */
public interface HudPlayerHead {

    /**
     * Returns the Base64-encoded skin texture value.
     *
     * @return the texture string
     */
    String getTexture();

    /**
     * Returns the Base64-encoded signature for the skin texture.
     *
     * @return the signature string
     */
    String getSignature();
}
