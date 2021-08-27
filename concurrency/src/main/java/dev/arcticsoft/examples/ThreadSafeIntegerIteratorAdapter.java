package dev.arcticsoft.examples;

public class ThreadSafeIntegerIteratorAdapter {

    private final IntegerIterator iterator = new IntegerIterator();

    public synchronized Integer getNextOrNull() {
        if (this.iterator.hasNext()) {
            return this.iterator.next();
        }
        return null;
    }
}
