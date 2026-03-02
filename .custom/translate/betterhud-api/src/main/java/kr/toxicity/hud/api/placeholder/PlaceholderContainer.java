package kr.toxicity.hud.api.placeholder;

import kr.toxicity.hud.api.yaml.YamlObject;

/**
 * Registry and factory for typed placeholder values.
 *
 * @param <T> the placeholder value type
 */
public interface PlaceholderContainer<T> {

    /**
     * Registers a named placeholder with this container.
     *
     * @param name        the placeholder identifier (used in YAML configs)
     * @param placeholder the {@link HudPlaceholder} implementation
     */
    void add(String name, HudPlaceholder<T> placeholder);

    /**
     * Retrieves a placeholder by name, optionally configuring it from a YAML node.
     *
     * @param name   the placeholder identifier
     * @param config optional YAML configuration for parameterised placeholders
     * @return the resolved {@link HudPlaceholder}, or {@code null} if not found
     */
    HudPlaceholder<T> get(String name, YamlObject config);
}
