package dev.arcticsoft.examples.atm;

public class BankAccount implements CurrentAccount {

    private int balance = 0;
    private final int accountNumber = 12345;
    private final String accountHolder = "Ravindu Kaveesha";
    Statement bankStatement = new Statement(accountHolder, accountNumber);

    @Override
    public synchronized int getBalance() {
        return balance;
    }

    @Override
    public synchronized int getAccountNumber() {
        return accountNumber;
    }

    @Override
    public synchronized String getAccountHolder() {
        return accountHolder;
    }

    @Override
    public synchronized void deposit(Transaction t) {
        balance += t.getAmount();
        bankStatement.addTransaction(t.getCID(), t.getAmount(), balance);
        notifyAll();
    }

    @Override
    public synchronized void withdrawal(Transaction t) {
        while (isOverdrawn(t)) {
            try {
                wait(); // add calling thread to 'wait set'
            } catch (InterruptedException e) {
            }
        }
        try {
            if (!isOverdrawn(t)) {
                balance -= t.getAmount();
                bankStatement.addTransaction(t.getCID(), t.getAmount(), balance);
                notifyAll();
            }
        } catch (Exception e) {
            System.out.println("Account balance is insufficient for the withdrawal!");
        }
    }

    @Override
    public synchronized boolean isOverdrawn(Transaction t) {
        return balance - t.getAmount() < 0;
    }

    @Override
    public synchronized void printStatement() {
        bankStatement.print();
    }
}
