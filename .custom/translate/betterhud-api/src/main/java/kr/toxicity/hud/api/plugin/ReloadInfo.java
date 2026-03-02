package kr.toxicity.hud.api.plugin;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Carries the context of a single reload invocation.
 *
 * @param sender the object that triggered the reload (e.g. a command sender or the plugin itself)
 * @param flags  the set of {@link ReloadFlagType} flags active for this reload
 */
public record ReloadInfo(Object sender, Set<ReloadFlagType> flags) {

    /**
     * Canonical constructor – ensures {@code flags} is an unmodifiable copy.
     *
     * @param sender the initiating sender
     * @param flags  the active flags
     */
    public ReloadInfo {
        Objects.requireNonNull(sender, "sender must not be null");
        flags = flags.isEmpty()
                ? Collections.emptySet()
                : Collections.unmodifiableSet(EnumSet.copyOf(flags));
    }

    /**
     * Returns {@code true} if {@code flag} is present in this reload's flag set.
     *
     * @param flag the flag to check
     * @return {@code true} when the flag is active
     */
    public boolean hasFlag(ReloadFlagType flag) {
        return flags.contains(flag);
    }
}
