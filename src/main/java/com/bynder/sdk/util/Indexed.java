package com.bynder.sdk.util;

/**
 * Couples an object with an index.
 *
 * @param <T> type of the object
 */
public class Indexed<T> {

    private final int index;
    private final T value;

    public Indexed(T value, int index) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Indexed<?>)) {
            return false;
        }
        Indexed<?> other = (Indexed<?>) o;
        return index == other.index && value != null && value.equals(other.value);
    }

}
