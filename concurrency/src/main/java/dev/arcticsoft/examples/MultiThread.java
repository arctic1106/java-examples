package dev.arcticsoft.examples;

public class MultiThread {

    private static class Thread1 implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println("Thread1 is running.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private static class Thread2 implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println("Thread2 is running.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        Thread t1 = new Thread(new Thread1());
        Thread t2 = new Thread(new Thread2());

        t1.start();
        t2.start();
        Thread.sleep(5000);
    }
}
