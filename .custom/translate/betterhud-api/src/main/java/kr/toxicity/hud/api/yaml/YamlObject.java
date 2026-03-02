package kr.toxicity.hud.api.yaml;

import java.io.File;
import java.util.Map;

/**
 * Represents a YAML mapping (object) node.
 * Provides typed helper methods for reading common configuration values.
 */
public interface YamlObject extends YamlElement, Iterable<Map.Entry<String, YamlElement>> {

    /**
     * Returns the raw underlying map.
     *
     * @return an unmodifiable {@link Map} of key → raw value
     */
    @Override
    Map<String, Object> get();

    /**
     * Returns the child element at {@code key}, or {@code null} if absent.
     *
     * @param key the key to look up
     * @return the child {@link YamlElement}, or {@code null}
     */
    YamlElement get(String key);

    /**
     * Merges the entries from {@code other} into this object.
     * Existing keys are not overwritten.
     *
     * @param other the source object
     * @return this object (for chaining)
     */
    YamlObject merge(YamlObject other);

    /**
     * Serialises this object back to a YAML file.
     *
     * @param file the target file
     */
    void save(File file);

    // -------------------------------------------------------------------------
    // Typed helpers
    // -------------------------------------------------------------------------

    /**
     * Returns the value at {@code key} as a {@code boolean}, or {@code def} if absent/wrong type.
     *
     * @param key the key
     * @param def default value
     * @return the boolean
     */
    default boolean getAsBoolean(String key, boolean def) {
        YamlElement el = get(key);
        return el != null ? el.asBoolean() : def;
    }

    /**
     * Returns the value at {@code key} as an {@code int}, or {@code def} if absent/wrong type.
     *
     * @param key the key
     * @param def default value
     * @return the int
     */
    default int getAsInt(String key, int def) {
        YamlElement el = get(key);
        return el != null ? el.asInt() : def;
    }

    /**
     * Returns the value at {@code key} as a {@code float}, or {@code def} if absent/wrong type.
     *
     * @param key the key
     * @param def default value
     * @return the float
     */
    default float getAsFloat(String key, float def) {
        YamlElement el = get(key);
        return el != null ? el.asFloat() : def;
    }

    /**
     * Returns the value at {@code key} as a {@code double}, or {@code def} if absent/wrong type.
     *
     * @param key the key
     * @param def default value
     * @return the double
     */
    default double getAsDouble(String key, double def) {
        YamlElement el = get(key);
        return el != null ? el.asDouble() : def;
    }

    /**
     * Returns the value at {@code key} as a {@code long}, or {@code def} if absent/wrong type.
     *
     * @param key the key
     * @param def default value
     * @return the long
     */
    default long getAsLong(String key, long def) {
        YamlElement el = get(key);
        return el != null ? el.asLong() : def;
    }

    /**
     * Returns the value at {@code key} as a {@link String}, or {@code def} if absent.
     *
     * @param key the key
     * @param def default value
     * @return the string
     */
    default String getAsString(String key, String def) {
        YamlElement el = get(key);
        return el != null ? el.asString() : def;
    }
}
