package dev.arcticsoft.examples;

/**
 * Reentrancy means that locks are acquired on a per-thread rather than
 * per-invocation basis.
 *
 * Intrinsic locks are reentrant, if a thread tries to acquire a lock that it
 * already holds, the requests succeed.
 */
public class Widget {

    public synchronized void doSomething() {
        System.out.println("Hello from Widget class.");
    }
}
