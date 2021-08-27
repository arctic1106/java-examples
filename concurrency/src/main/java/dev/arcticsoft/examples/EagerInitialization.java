package dev.arcticsoft.examples;

/**
 * Eager initialization
 *
 * Using eager initialization eliminates the synchronization cost incurred on
 * each call to getInstance in {@link SafeLazyInitialization}
 *
 */
public class EagerInitialization {

    static {
        System.out.println("Initializing static resource.");
    }
    private static final Resource resource = new Resource();

    static Resource getResource() {
        System.out.println("In getResource().");
        return resource;
    }

    /**
     * The main method starts after the resource is statically initialized, thus
     * the resource is eagerly loaded.
     */
    public static void main(final String[] args) {
        System.out.println("Start of main.");
        EagerInitialization.getResource();
        System.out.println("End of main.");
    }

    static class Resource {
    }
}
