package dev.arcticsoft.examples;

public class CreateThread2 implements Runnable {

    @Override
    public void run() {
        System.out.println("thread is running...");
    }

    public static void main(String args[]) {
        CreateThread2 obj = new CreateThread2();
        Thread trd = new Thread(obj);
        trd.start();
    }
}
