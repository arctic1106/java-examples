private static Object spoon = new Object();
private static Object bowl = new Object();

void main() {
    var cook1 = new Thread(() -> {
        synchronized (spoon) {
            IO.println("Cook1: Holding the spoon... Waiting for the bowl...");
            synchronized (bowl) {
                IO.println("Cook1: Holding the spoon and the bowl.");
            }
        }
    });
    cook1.start();

    var cook2 = new Thread(() -> {
        synchronized (spoon) {
            IO.println("Cook2: Holding the bowl... Waiting for the spoon...");
            synchronized (bowl) {
                IO.println("Cook2: Holding the spoon and the bowl.");
            }
        }
    });
    cook2.start();
}
