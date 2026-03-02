package kr.toxicity.hud.api.manager;

import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

/**
 * Manages GLSL shader generation for the HUD resource pack.
 * Allows external plugins to inject shader constants and custom tags.
 */
public interface ShaderManager {

    /**
     * Identifies the type of GLSL shader being modified.
     */
    enum ShaderType {
        /** The vertex shader for text rendering. */
        TEXT_VERTEX,
        /** The fragment shader for text rendering. */
        TEXT_FRAGMENT
    }

    /**
     * A single GLSL tag to be injected into a shader.
     *
     * @param name  the GLSL symbol name
     * @param value the GLSL value expression
     */
    record ShaderTag(String name, String value) {}

    /**
     * Supplies one or more {@link ShaderTag} instances to be injected into a shader.
     */
    @FunctionalInterface
    interface ShaderTagSupplier {
        /**
         * Generates the shader tags.
         *
         * @return an iterable of {@link ShaderTag} entries
         */
        Iterable<ShaderTag> get();
    }

    /**
     * Registers a GLSL {@code #define} constant that will be injected into all shaders.
     *
     * @param key   the constant name
     * @param value the constant value expression
     */
    void addConstant(String key, String value);

    /**
     * Registers a supplier of shader tags for a specific shader type.
     *
     * @param type     the target {@link ShaderType}
     * @param supplier the {@link ShaderTagSupplier} that generates the tags
     */
    void addTagSupplier(ShaderType type, ShaderTagSupplier supplier);
}
