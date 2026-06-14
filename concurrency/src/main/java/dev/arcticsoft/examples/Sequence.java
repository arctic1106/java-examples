package dev.arcticsoft.examples;

/**
 * Sequence
 *
 * @list 1.2
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 */
public class Sequence {

    private int nextValue;

    // synchronized makes sure only one thread accesses this function at a time.
    public synchronized int getNext() {
        return nextValue++;
    }
}
