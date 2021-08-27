package dev.arcticsoft.examples.atm;

public class StatementEntry {

    private final String CustomerID;
    private final int amount;
    private final int currentBal;   // The balance after the transaction

    public StatementEntry(String CustomerID, int amount, int currentBal) {
        this.CustomerID = CustomerID;
        this.amount = amount;
        this.currentBal = currentBal;
    }

    public String getCustomer() {
        return CustomerID;
    }

    public int getAmount() {
        return amount;
    }

    public int getCurrentBalance() {
        return currentBal;
    }

    public String toStringOLD() {
        char TAB = '\t';
        return "Customer: " + CustomerID + "," + TAB
                + "Amount: " + amount + ", " + TAB
                + "Balance: " + currentBal;
    }

}
