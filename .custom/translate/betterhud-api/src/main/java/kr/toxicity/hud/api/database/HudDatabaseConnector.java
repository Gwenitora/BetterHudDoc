package kr.toxicity.hud.api.database;

import kr.toxicity.hud.api.yaml.YamlObject;

/**
 * Factory that creates a {@link HudDatabase} from a YAML configuration block.
 */
public interface HudDatabaseConnector {

    /**
     * Connects to the database described by {@code config} and returns an open {@link HudDatabase}.
     *
     * @param config the YAML configuration node for this database
     * @return the opened {@link HudDatabase} instance
     */
    HudDatabase connect(YamlObject config);
}
