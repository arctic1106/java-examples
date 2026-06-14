package dev.arcticsoft.examples;

import java.util.concurrent.TimeUnit;

public class ThreadSafeClient2 {

    private final IntegerIteratorServerLocked iterator = new IntegerIteratorServerLocked();

    public void printIterator() {
        while (true) {
            var nextValue = this.iterator.getNextOrNull();
            if (nextValue == null) {
                System.out.printf("Thread %s, Iterator returned null, it has reached the limit. \n", Thread.currentThread().threadId());
                break;
            }
            boolean finished = false;
            if (nextValue == IntegerIterator.MAX_SIZE - 1 && "first".equals(Thread.currentThread().getName())) {
                try {
                    System.out.printf("""
							Thread %s, Iterator has reached %s value, we want to wait for the other thread to
                            possibly break the iterator limit, but we use server based locking, thus
							nothing bad happens.""",
                            Thread.currentThread().threadId(),
                            nextValue
                    );
                    TimeUnit.MILLISECONDS.sleep(100);
                    finished = true;
                } catch (final InterruptedException e) {
                }
            }
            if (finished) {
                System.out.printf("Thread %s, waiting finished.  \n", Thread.currentThread().threadId());
            }
            System.out.printf("Thread %s, next value returned by iterator %s \n", Thread.currentThread().threadId(), nextValue);
        }
    }

    public IntegerIteratorServerLocked getIterator() {
        return this.iterator;
    }
}
