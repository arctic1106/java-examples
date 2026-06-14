package dev.arcticsoft.examples.atm;

public class Transaction {

    private final String CustomerID;
    private final int amount;

    public Transaction(String CustomerID, int amount) {
        this.CustomerID = CustomerID;
        this.amount = amount;
    }

    public String getCID() {
        return CustomerID;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "[ "
                + "Customer: " + CustomerID + ", "
                + "Amount: " + amount
                + "]";
    }
}
