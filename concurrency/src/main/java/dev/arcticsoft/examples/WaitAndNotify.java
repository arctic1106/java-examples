package dev.arcticsoft.examples;

public class WaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        var b = new ThreadX();
        b.start();

        synchronized (b) {
            System.out.println("Waiting for b to complete...");
            b.wait();
            System.out.println("Execution back to main Thread");
        }
    }
}

class ThreadX extends Thread {

    public int total;

    @Override
    public void run() {
        synchronized (this) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            notify();
        }
    }
}
