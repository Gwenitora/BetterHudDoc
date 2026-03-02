package kr.toxicity.hud.api.yaml;

import java.util.List;

/**
 * Represents a YAML sequence (list) node.
 * Extends {@link YamlElement} so it can be accessed from any element reference.
 */
public interface YamlArray extends YamlElement, Iterable<YamlElement> {

    /**
     * Returns the raw underlying list of values.
     *
     * @return an unmodifiable {@link List} of raw objects
     */
    @Override
    List<Object> get();
}
