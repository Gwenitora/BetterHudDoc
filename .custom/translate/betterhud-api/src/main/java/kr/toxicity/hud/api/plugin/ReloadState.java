package kr.toxicity.hud.api.plugin;

import java.util.Map;

/**
 * Sealed interface representing the possible outcomes of a plugin reload.
 *
 * <p>Use a pattern switch to handle each case:
 * <pre>{@code
 * switch (state) {
 *     case ReloadState.Success s  -> handleSuccess(s);
 *     case ReloadState.Failure f  -> handleFailure(f);
 *     case ReloadState.OnReload o -> handleOnReload();
 * }
 * }</pre>
 * </p>
 */
public sealed interface ReloadState permits ReloadState.Success, ReloadState.Failure, ReloadState.OnReload {

    /**
     * Returned when a reload completes successfully.
     *
     * @param pack the generated resource-pack file map (path → bytes)
     * @param time elapsed reload time in milliseconds
     */
    record Success(Map<String, byte[]> pack, long time) implements ReloadState {}

    /**
     * Returned when a reload fails due to an exception.
     *
     * @param cause the root cause throwable
     */
    record Failure(Throwable cause) implements ReloadState {}

    /**
     * Returned immediately when a reload is requested while another reload is already in progress.
     */
    final class OnReload implements ReloadState {

        /** Singleton instance – there is only one "already reloading" state. */
        public static final OnReload INSTANCE = new OnReload();

        /** Private – use {@link #INSTANCE}. */
        private OnReload() {}
    }
}
