package dev.arcticsoft.examples.atm;

public class Grandmother extends Thread {

    private final CurrentAccount account;

    public Grandmother(ThreadGroup threadGroup, CurrentAccount account) {

        super(threadGroup, "Grandmother"); // Thread( thread_name )
        this.account = account;
    }

    @Override
    public void run() {
        System.out.println("Started the thread '" + getName() + "' for transaction.");
        Transaction birthdayMoney = new Transaction(getName(), 500);
        account.deposit(birthdayMoney);
        String grandmotherName = "Janice";
        System.out.println(getName() + "(" + grandmotherName + "): Deposited " + birthdayMoney.getAmount() + ".");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }

        Transaction christmasMoney = new Transaction(getName(), 800);
        account.deposit(christmasMoney);
        System.out.println(getName() + "(" + grandmotherName + "): Deposited " + christmasMoney.getAmount() + ".");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }

        System.out.println(getName() + " TERMINATED.");
    }
}
