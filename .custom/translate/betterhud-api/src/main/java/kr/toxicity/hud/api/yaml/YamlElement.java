package kr.toxicity.hud.api.yaml;

import java.util.List;

/**
 * Represents a YAML scalar or composite value node.
 * Provides typed accessor methods for common conversions.
 */
public interface YamlElement extends YamlConfiguration {

    /**
     * Returns the raw underlying value of this node.
     *
     * @return the raw Java object (String, Number, Boolean, List, Map …)
     */
    Object get();

    /**
     * Returns the value as a {@link String}.
     *
     * @return string representation
     */
    String asString();

    /**
     * Returns the value as an {@code int}.
     *
     * @return integer value
     */
    int asInt();

    /**
     * Returns the value as a {@code float}.
     *
     * @return float value
     */
    float asFloat();

    /**
     * Returns the value as a {@code double}.
     *
     * @return double value
     */
    double asDouble();

    /**
     * Returns the value as a {@code long}.
     *
     * @return long value
     */
    long asLong();

    /**
     * Returns the value as a {@code boolean}.
     *
     * @return boolean value
     */
    boolean asBoolean();

    /**
     * Tries to interpret this node as a YAML sequence.
     *
     * @return this node as a {@link YamlArray}, or {@code null} if not applicable
     */
    YamlArray asArray();

    /**
     * Tries to interpret this node as a YAML mapping.
     *
     * @return this node as a {@link YamlObject}, or {@code null} if not applicable
     */
    YamlObject asObject();
}
