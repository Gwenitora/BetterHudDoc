package kr.toxicity.hud.api.popup;

import java.util.UUID;

/**
 * A single entry in a popup queue.
 * Tracks display index, uniqueness, availability, and sort type.
 */
public interface PopupIterator {

    /**
     * Advances to the next popup entry and returns it.
     *
     * @return the next {@link PopupIterator} entry, or {@code null} if exhausted
     */
    PopupIterator next();

    /**
     * Returns the parent iterator group that owns this entry.
     *
     * @return the owning {@link PopupIteratorGroup}
     */
    PopupIteratorGroup parent();

    /**
     * Returns {@code true} if this popup should appear at most once at a time.
     *
     * @return {@code true} when unique
     */
    boolean isUnique();

    /**
     * Returns the current display index of this entry within its group.
     *
     * @return the display index
     */
    int getIndex();

    /**
     * Sets the display index of this entry.
     *
     * @param index the new index
     */
    void setIndex(int index);

    /**
     * Returns {@code true} if this iterator still has entries available to show.
     *
     * @return {@code true} when available
     */
    boolean available();

    /**
     * Removes this entry from its group.
     */
    void remove();

    /**
     * Returns the UUID associated with this popup entry (e.g. the entity UUID).
     *
     * @return the entry UUID
     */
    UUID getUUID();

    /**
     * Returns the sort ordering used for this entry.
     *
     * @return the {@link PopupSortType}
     */
    PopupSortType getSortType();
}
