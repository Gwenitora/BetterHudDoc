package kr.toxicity.hud.yaml;

import kr.toxicity.hud.api.yaml.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of {@link YamlObject} backed by a {@link Map} loaded by SnakeYAML.
 */
public class YamlObjectImpl implements YamlObject {

    /** The raw key-value map from SnakeYAML. */
    private final Map<String, Object> rawMap;

    /** Dot-separated path of this node in the configuration tree. */
    private final String path;

    /**
     * Wraps an existing raw map.
     *
     * @param rawMap the SnakeYAML-deserialized map
     * @param path   the node path (empty string for the root)
     */
    public YamlObjectImpl(Map<String, Object> rawMap, String path) {
        this.rawMap = Objects.requireNonNull(rawMap, "rawMap must not be null");
        this.path = path != null ? path : "";
    }

    /** {@inheritDoc} */
    @Override
    public String path() { return path; }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> get() {
        return Collections.unmodifiableMap(rawMap);
    }

    /** {@inheritDoc} */
    @Override
    public YamlElement get(String key) {
        Object val = rawMap.get(key);
        if (val == null) return null;
        String childPath = path.isEmpty() ? key : path + "." + key;
        return wrapValue(val, childPath);
    }

    /** {@inheritDoc} */
    @Override
    public YamlObject merge(YamlObject other) {
        other.forEach(entry -> rawMap.putIfAbsent(entry.getKey(), entry.getValue().get()));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void save(File file) {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        try (FileWriter fw = new FileWriter(file)) {
            yaml.dump(rawMap, fw);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save YAML to " + file, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<Map.Entry<String, YamlElement>> iterator() {
        List<Map.Entry<String, YamlElement>> entries = new ArrayList<>();
        for (Map.Entry<String, Object> e : rawMap.entrySet()) {
            String childPath = path.isEmpty() ? e.getKey() : path + "." + e.getKey();
            entries.add(Map.entry(e.getKey(), wrapValue(e.getValue(), childPath)));
        }
        return entries.iterator();
    }

    // -------------------------------------------------------------------------
    // YamlElement scalar accessors (self-access for root/merged nodes)
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override public String asString() { return rawMap.toString(); }

    /** {@inheritDoc} */
    @Override public int asInt() { return 0; }

    /** {@inheritDoc} */
    @Override public float asFloat() { return 0f; }

    /** {@inheritDoc} */
    @Override public double asDouble() { return 0d; }

    /** {@inheritDoc} */
    @Override public long asLong() { return 0L; }

    /** {@inheritDoc} */
    @Override public boolean asBoolean() { return false; }

    /** {@inheritDoc} */
    @Override public YamlArray asArray() { return null; }

    /** {@inheritDoc} */
    @Override public YamlObject asObject() { return this; }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Wraps a raw SnakeYAML value in the appropriate {@link YamlElement} implementation.
     *
     * @param val  the raw value
     * @param path the dot-separated node path
     * @return the wrapped element
     */
    @SuppressWarnings("unchecked")
    private YamlElement wrapValue(Object val, String path) {
        if (val instanceof Map<?, ?> m) {
            Map<String, Object> typed = new LinkedHashMap<>();
            m.forEach((k, v) -> typed.put(String.valueOf(k), v));
            return new YamlObjectImpl(typed, path);
        }
        if (val instanceof List<?> list) {
            return new YamlArrayImpl((List<Object>) list, path);
        }
        return new YamlScalarImpl(val, path);
    }
}
