package dev.arcticsoft.examples;

public class LoggingWidget extends Widget {

    // Thread-A tries to acquire the lock on the Widget object as doSomething method is synchronized
    @Override
    public synchronized void doSomething() {
        System.out.println(this.toString() + ": calling doSomething");
        /*
        *  Thread-A again tries to acquire the lock on the Widget class and the request succeeds because intrinsic locks
        *  are reentrant, thus Thread-A can acquire the lock it already holds.
        *
        *  If intrinsic locks were not reentrant, the call to super.doSomething would never be able to acquire
        *  the lock because it would be considered already held and the Thread-A would wait forever for a lock it
        * can never acquire.
        * */
        super.doSomething();
    }
}
