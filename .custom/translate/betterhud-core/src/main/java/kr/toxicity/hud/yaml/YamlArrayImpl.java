package kr.toxicity.hud.yaml;

import kr.toxicity.hud.api.yaml.*;

import java.util.*;

/**
 * Implementation of {@link YamlArray} backed by a raw {@link List} from SnakeYAML.
 */
public class YamlArrayImpl implements YamlArray {

    /** The raw list of values. */
    private final List<Object> rawList;

    /** Dot-separated node path. */
    private final String path;

    /**
     * Constructs a YAML array wrapper.
     *
     * @param rawList the SnakeYAML list
     * @param path    the node path
     */
    public YamlArrayImpl(List<Object> rawList, String path) {
        this.rawList = Objects.requireNonNull(rawList);
        this.path = path != null ? path : "";
    }

    /** {@inheritDoc} */
    @Override public String path() { return path; }

    /** {@inheritDoc} */
    @Override public List<Object> get() { return Collections.unmodifiableList(rawList); }

    /** {@inheritDoc} */
    @Override public String asString() { return rawList.toString(); }

    /** {@inheritDoc} */
    @Override public int asInt() { return rawList.size(); }

    /** {@inheritDoc} */
    @Override public float asFloat() { return rawList.size(); }

    /** {@inheritDoc} */
    @Override public double asDouble() { return rawList.size(); }

    /** {@inheritDoc} */
    @Override public long asLong() { return rawList.size(); }

    /** {@inheritDoc} */
    @Override public boolean asBoolean() { return !rawList.isEmpty(); }

    /** {@inheritDoc} */
    @Override public YamlArray asArray() { return this; }

    /** {@inheritDoc} */
    @Override public YamlObject asObject() { return null; }

    /** {@inheritDoc} */
    @Override
    public Iterator<YamlElement> iterator() {
        List<YamlElement> elements = new ArrayList<>();
        for (int i = 0; i < rawList.size(); i++) {
            elements.add(new YamlScalarImpl(rawList.get(i), path + "[" + i + "]"));
        }
        return elements.iterator();
    }
}
