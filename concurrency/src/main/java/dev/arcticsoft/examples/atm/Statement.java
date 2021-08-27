package dev.arcticsoft.examples.atm;

public class Statement {

    private final int MAX_TRANS = 20;
    private final StatementEntry[] statement = new StatementEntry[MAX_TRANS];
    private final String accountHolder;
    private final int accountNumber;
    private int transactionCount = 0;

    public Statement(String accountHolder, int accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
    }

    public void addTransaction(String CustomerID, int amount, int balance) {
        // Create a new statement entry & add to the statement
        statement[transactionCount] = new StatementEntry(CustomerID, amount, balance);
        transactionCount++;
    }

    public void print() {
        System.out.println();
        System.out.println("Statement for " + accountHolder
                + "'s Account: " + accountNumber);
        System.out.println("================================================");
        System.out.format("%1$-20s %2$10s  %3$13s", "Customer", "Amount", "Balance");
        System.out.println();
        System.out.println("================================================");

        for (int tid = 0; tid < transactionCount; tid++) {
            System.out.format("%1$-20s %2$10d  %3$10d",
                    statement[tid].getCustomer(),
                    statement[tid].getAmount(),
                    statement[tid].getCurrentBalance());
            System.out.println();
        }

        System.out.println("================================================");
        System.out.println();
    }
}
