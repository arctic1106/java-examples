package dev.arcticsoft.examples;

/**
 * This is an example of CHECK-THEN-ACT operation.
 *
 * To ensure thread safety, the check-then-act operations and read-modify-write
 * operations must be atomic. We call them COMPOUND ACTIONS: sequences of
 * operations that must be executed atomically in order to remain thread-safe.
 */
public class LazyInitRace {

    private ExpensiveObject instance = null;

    /**
     * This compound action is not executed atomically
     *
     */
    public ExpensiveObject getInstance() {
        if (this.instance == null) {
            this.instance = new ExpensiveObject();
        }
        return this.instance;
    }
}
