package kr.toxicity.hud.yaml;

import kr.toxicity.hud.api.yaml.*;

/**
 * Implementation of {@link YamlElement} for scalar (non-map, non-list) YAML values.
 */
public class YamlScalarImpl implements YamlElement {

    /** The raw scalar value. */
    private final Object value;

    /** Dot-separated node path. */
    private final String path;

    /**
     * Constructs a scalar element.
     *
     * @param value the raw value
     * @param path  the node path
     */
    public YamlScalarImpl(Object value, String path) {
        this.value = value;
        this.path = path != null ? path : "";
    }

    /** {@inheritDoc} */
    @Override public String path() { return path; }

    /** {@inheritDoc} */
    @Override public Object get() { return value; }

    /** {@inheritDoc} */
    @Override
    public String asString() {
        return value == null ? "" : value.toString();
    }

    /** {@inheritDoc} */
    @Override
    public int asInt() {
        if (value instanceof Number n) return n.intValue();
        try { return Integer.parseInt(asString()); } catch (NumberFormatException e) { return 0; }
    }

    /** {@inheritDoc} */
    @Override
    public float asFloat() {
        if (value instanceof Number n) return n.floatValue();
        try { return Float.parseFloat(asString()); } catch (NumberFormatException e) { return 0f; }
    }

    /** {@inheritDoc} */
    @Override
    public double asDouble() {
        if (value instanceof Number n) return n.doubleValue();
        try { return Double.parseDouble(asString()); } catch (NumberFormatException e) { return 0d; }
    }

    /** {@inheritDoc} */
    @Override
    public long asLong() {
        if (value instanceof Number n) return n.longValue();
        try { return Long.parseLong(asString()); } catch (NumberFormatException e) { return 0L; }
    }

    /** {@inheritDoc} */
    @Override
    public boolean asBoolean() {
        if (value instanceof Boolean b) return b;
        return Boolean.parseBoolean(asString());
    }

    /** {@inheritDoc} */
    @Override public YamlArray asArray() { return null; }

    /** {@inheritDoc} */
    @Override public YamlObject asObject() { return null; }
}
