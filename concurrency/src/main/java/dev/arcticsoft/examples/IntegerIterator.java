package dev.arcticsoft.examples;

import java.util.Iterator;

public class IntegerIterator implements Iterator<Integer> {

    public static int MAX_SIZE = 1000;
    private int index = 0;

    @Override
    public boolean hasNext() {
        return (index < MAX_SIZE);
    }

    @Override
    public Integer next() {
        return index++;
    }
}
