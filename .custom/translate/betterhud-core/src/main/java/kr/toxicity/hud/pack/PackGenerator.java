package kr.toxicity.hud.pack;

import kr.toxicity.hud.api.plugin.ReloadInfo;

import java.util.*;
import java.util.function.Supplier;

/**
 * Assembles the resource pack during a reload.
 *
 * <p>Managers register file-generation tasks via {@link #addTask}.
 * After all managers complete their reload, {@link #generate} executes every task
 * and collects the results into a {@code Map&lt;path, bytes&gt;} that can be served
 * as a resource-pack ZIP.</p>
 */
public final class PackGenerator {

    /** Pending file-generation tasks keyed by their pack-relative path. */
    private static final Map<String, Supplier<byte[]>> tasks = new TreeMap<>();

    /** Prevent instantiation – this is a static utility class. */
    private PackGenerator() {}

    /**
     * Registers a file to be included in the generated resource pack.
     *
     * @param path     the path segments (joined with {@code "/"}) of the file within the pack
     * @param supplier the supplier that generates the file bytes on demand
     */
    public static void addTask(List<String> path, Supplier<byte[]> supplier) {
        Objects.requireNonNull(path, "path must not be null");
        Objects.requireNonNull(supplier, "supplier must not be null");
        String joined = String.join("/", path);
        tasks.put(joined, supplier);
    }

    /**
     * Executes all registered tasks and returns the complete resource-pack file map.
     *
     * @param info metadata about the current reload (unused here, available for extensions)
     * @return an unmodifiable map of pack-relative path → file bytes
     */
    public static Map<String, byte[]> generate(ReloadInfo info) {
        Map<String, byte[]> result = new TreeMap<>();
        for (Map.Entry<String, Supplier<byte[]>> entry : tasks.entrySet()) {
            byte[] bytes = entry.getValue().get();
            if (bytes != null) {
                result.put(entry.getKey(), bytes);
            }
        }
        clearTasks();
        return Collections.unmodifiableMap(result);
    }

    /**
     * Removes all pending tasks.
     * Called automatically by {@link #generate} after pack assembly.
     */
    public static void clearTasks() {
        tasks.clear();
    }
}
