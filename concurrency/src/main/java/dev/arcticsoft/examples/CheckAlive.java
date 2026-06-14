package dev.arcticsoft.examples;

public class CheckAlive implements Runnable {

    @Override
    public void run() {
        System.out.println("My thread is in running state.");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String args[]) throws InterruptedException {
        CheckAlive obj = new CheckAlive();
        Thread trd = new Thread(obj);
        trd.start();

        while (true) {
            if (trd.isAlive()) {
                System.out.println("Alive");
            } else {
                System.out.println("Dead");
            }
            Thread.sleep(500);
        }
    }
}
