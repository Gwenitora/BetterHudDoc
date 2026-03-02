package kr.toxicity.hud.animation;

/**
 * Controls how a multi-frame animation sequence is played.
 */
public enum AnimationType {

    /**
     * The animation repeats indefinitely from the first frame after the last.
     */
    LOOP,

    /**
     * The animation plays once and then stops on the last frame.
     */
    PLAY_ONCE,

    /**
     * The animation plays forward then backward, repeating in a ping-pong pattern.
     */
    PLAY_IT_BACKWARD,

    /**
     * The animation plays forward then backward once, then stops.
     */
    PLAY_IT_BACKWARD_ONCE
}
