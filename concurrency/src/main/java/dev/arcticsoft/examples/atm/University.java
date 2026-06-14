package dev.arcticsoft.examples.atm;

public class University extends Thread {

    private final CurrentAccount account;

    public University(ThreadGroup threadGroup, CurrentAccount account) {

        super(threadGroup, "University"); // Thread( thread_name )
        this.account = account;
    }

    @Override
    public void run() {
        System.out.println("Started the thread '" + getName() + "' for transaction.");
        Transaction courseFee1 = new Transaction(getName(), 10000);
        account.withdrawal(courseFee1);
        String universityName = "IIT";
        System.out.println(getName() + "(" + universityName + "): Withdrew " + courseFee1.getAmount() + ".");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }

        Transaction courseFee2 = new Transaction(getName(), 20000);
        account.withdrawal(courseFee2);
        System.out.println(getName() + "(" + universityName + "): Withdrew " + courseFee2.getAmount() + ".");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }

        Transaction courseFee3 = new Transaction(getName(), 30000);
        account.withdrawal(courseFee3);
        System.out.println(getName() + "(" + universityName + "): Withdrew " + courseFee3.getAmount() + ".");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }

        System.out.println(getName() + " TERMINATED.");

    }
}
