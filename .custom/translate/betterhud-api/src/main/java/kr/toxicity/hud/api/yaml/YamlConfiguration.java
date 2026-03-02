package kr.toxicity.hud.api.yaml;

/**
 * Base interface for all YAML configuration nodes.
 * Every node knows its path within the configuration tree and can be compared
 * for ordering (alphabetical by path).
 */
public interface YamlConfiguration extends Comparable<YamlConfiguration> {

    /**
     * Returns the dot-separated path of this node within the configuration tree.
     * The root node returns an empty string.
     *
     * @return the node path
     */
    String path();

    /**
     * Default comparison by path string (alphabetical).
     *
     * @param other the other configuration node
     * @return comparison result
     */
    @Override
    default int compareTo(YamlConfiguration other) {
        return this.path().compareTo(other.path());
    }
}
