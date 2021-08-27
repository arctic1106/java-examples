package dev.arcticsoft.examples;

import java.math.BigInteger;

/**
 * ExpensiveFunction takes a long time to compute its result; we'd like to
 * create a Computable wrapper that remembers the result of previous
 * computations and encapsulates the caching process. This technique is known as
 * memoization.
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(final String arg) {
        // after deep thought...
        return new BigInteger(arg);
    }
}
