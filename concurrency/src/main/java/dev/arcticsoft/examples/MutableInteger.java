package dev.arcticsoft.examples;

/**
 * This mutable integer is not thread-safe.
 *
 * If one thread calls the "set" method, the other threads calling "get" method
 * may or may not see that update.
 */
public class MutableInteger {

    private int value;

    public int get() {
        return this.value;
    }

    public void set(final int value) {
        this.value = value;
    }
}
