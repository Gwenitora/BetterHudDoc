package kr.toxicity.hud.api.manager;

import kr.toxicity.hud.api.placeholder.PlaceholderContainer;

/**
 * Provides typed placeholder containers for number, boolean, and string values.
 */
public interface PlaceholderManager {

    /**
     * Returns the container for numeric (double) placeholders.
     *
     * @return the number {@link PlaceholderContainer}
     */
    PlaceholderContainer<Double> getNumberContainer();

    /**
     * Returns the container for boolean placeholders.
     *
     * @return the boolean {@link PlaceholderContainer}
     */
    PlaceholderContainer<Boolean> getBooleanContainer();

    /**
     * Returns the container for string placeholders.
     *
     * @return the string {@link PlaceholderContainer}
     */
    PlaceholderContainer<String> getStringContainer();
}
