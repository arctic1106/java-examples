package dev.arcticsoft.examples;

/**
 * Holder
 *
 * @list 3.15
 * @smell Bad
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * This class at risk of failure if not properly published.
 */
public class Holder {

    public final int n;

    public Holder(int n) {
        this.n = n;
    }

    // A thread other than the publishing thread were to call assertSanity, it could throw AssertionError.
    public void assertSanity() {
        // A thread != n) 
        {
            throw new AssertionError("This statement is false.");
        }
    }
}
