package kr.toxicity.hud.yaml;

import kr.toxicity.hud.api.yaml.YamlObject;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for loading {@link YamlObject} instances from files or streams.
 */
public final class YamlConfigurationImpl {

    /** Prevent instantiation. */
    private YamlConfigurationImpl() {}

    /**
     * Loads a {@link YamlObject} from the given {@link File}.
     *
     * @param file the YAML file to read
     * @return the parsed {@link YamlObject}
     * @throws IOException if the file cannot be read
     */
    public static YamlObject load(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return load(is, file.getName());
        }
    }

    /**
     * Loads a {@link YamlObject} from an {@link InputStream}.
     *
     * @param stream the input stream to read YAML from
     * @param path   a descriptive path used for error messages and the root node path
     * @return the parsed {@link YamlObject}
     * @throws IOException if reading fails
     */
    @SuppressWarnings("unchecked")
    public static YamlObject load(InputStream stream, String path) throws IOException {
        Yaml yaml = new Yaml();
        try {
            Object obj = yaml.load(stream);
            if (obj instanceof Map<?, ?> m) {
                Map<String, Object> typed = new LinkedHashMap<>();
                m.forEach((k, v) -> typed.put(String.valueOf(k), v));
                return new YamlObjectImpl(typed, path);
            }
            // Return empty object if the file is empty or not a mapping
            return new YamlObjectImpl(new LinkedHashMap<>(), path);
        } catch (Exception e) {
            throw new IOException("Failed to parse YAML from: " + path, e);
        }
    }
}
