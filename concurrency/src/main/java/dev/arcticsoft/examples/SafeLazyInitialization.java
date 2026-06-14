package dev.arcticsoft.examples;

/**
 * Thread-safe lazy initialization
 *
 * {@link UnsafeLazyInitialization} can be fixed by making the getResource
 * method synchronized as shown here in SafeLazyInitialization.
 *
 * Because the code path through getInstance is fairly short (a test and a
 * predicted branch), if getInstance is not called frequently by many threads,
 * there is little contention for the SafeLazyInitialization lock that this
 * approach offers adequate performance.
 */
public class SafeLazyInitialization {

    private static Resource resource;

    public synchronized static Resource getInstance() {
        if (resource == null) {
            resource = new Resource();
        }
        return resource;
    }

    static class Resource {
    }
}
