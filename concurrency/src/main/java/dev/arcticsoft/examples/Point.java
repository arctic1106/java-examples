package dev.arcticsoft.examples;

/**
 * Point
 * <p/>
 * Immutable Point class used by DelegatingVehicleTracker
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Point {

    public final int x, y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
