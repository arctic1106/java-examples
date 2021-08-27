package dev.arcticsoft.examples.atm;

public class Student extends Thread {

    private final CurrentAccount account;

    public Student(ThreadGroup threadGroup, CurrentAccount account) {

        super(threadGroup, "Student"); // Thread( thread_name )
        this.account = account;
    }

    @Override
    public void run() {
        System.out.println("Started the thread '" + getName() + "' for transaction.");
        Transaction winLottery = new Transaction(getName(), 1000000);
        account.deposit(winLottery);
        String studentName = "Ravindu";
        System.out.println(getName() + "(" + studentName + "): Deposited " + winLottery.getAmount() + ".");
        try {
            sleep(4000);
        } catch (InterruptedException e) {
        }

        Transaction winLottery2 = new Transaction(getName(), 20000);
        account.deposit(winLottery2);
        System.out.println(getName() + "(" + studentName + "): Deposited " + winLottery2.getAmount() + ".");
        try {
            sleep(4000);
        } catch (InterruptedException e) {
        }

        Transaction buyPhone = new Transaction(getName(), 1000);
        account.withdrawal(buyPhone);
        System.out.println(getName() + "(" + studentName + "): Withdrew " + buyPhone.getAmount() + ".");
        try {
            sleep(4000);
        } catch (InterruptedException e) {
        }
        System.out.println(getName() + " TERMINATED.");
        account.printStatement();

    }
}
