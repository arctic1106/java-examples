package dev.arcticsoft.examples;

public class CreateThread1 extends Thread {

    @Override
    public void run() {
        System.out.println("thread is running...");
    }

    public static void main(String[] args) {
        CreateThread1 trd = new CreateThread1();
        trd.start();
    }
}
