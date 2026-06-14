package dev.arcticsoft.examples;

public class CountingSheep {

    volatile boolean asleep;

    public void tryToSleep() {
        while (!asleep) // The thread can be notified when asleep has been set by another thread.
        {
            countSomeSheep();
        }
    }

    public void countSomeSheep() {
        // One, two, three...
    }
}
