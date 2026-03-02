package kr.toxicity.hud.api.popup;

/**
 * Groups multiple {@link PopupIterator} entries and manages which one is currently displayed.
 */
public interface PopupIteratorGroup {

    /**
     * Advances the group to the next iterator and returns it.
     *
     * @return the next active {@link PopupIterator}, or {@code null} if empty
     */
    PopupIterator next();

    /**
     * Adds a new iterator entry to this group.
     *
     * @param iterator the {@link PopupIterator} to add
     */
    void addIterator(PopupIterator iterator);

    /**
     * Removes all iterator entries from this group.
     */
    void clear();

    /**
     * Returns the current active display index.
     *
     * @return the display index
     */
    int getIndex();

    /**
     * Returns {@code true} if an entry with the given name exists in this group.
     *
     * @param name the name to look up
     * @return {@code true} when present
     */
    boolean contains(String name);
}
