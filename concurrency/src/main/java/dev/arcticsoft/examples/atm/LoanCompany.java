package dev.arcticsoft.examples.atm;

public class LoanCompany extends Thread {

    private final CurrentAccount account;

    public LoanCompany(ThreadGroup threadGroup, CurrentAccount account) {

        super(threadGroup, "Loan Company"); // Thread( thread_name )
        this.account = account;
    }

    @Override
    public void run() {
        System.out.println("Started the thread '" + getName() + "' for transaction.");
        Transaction loan1 = new Transaction(getName(), 3000);
        account.deposit(loan1);
        String loanCompanyName = "Aqua";
        System.out.println(getName() + "(" + loanCompanyName + "): Deposited " + loan1.getAmount() + ".");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
        }

        Transaction loan2 = new Transaction(getName(), 3500);
        account.deposit(loan2);
        System.out.println(getName() + "(" + loanCompanyName + "): Deposited " + loan2.getAmount() + ".");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
        }

        Transaction loan3 = new Transaction(getName(), 4000);
        account.deposit(loan3);
        System.out.println(getName() + "(" + loanCompanyName + "): Deposited " + loan3.getAmount() + ".");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println(getName() + " TERMINATED.");

    }

}
