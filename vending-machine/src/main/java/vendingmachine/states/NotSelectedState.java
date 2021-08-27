package vendingmachine.states;

import vendingmachine.money.Coin;
import vendingmachine.product.Product;
import vendingmachine.VendingMachine;
import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachinePurchaseException;
import vendingmachine.exceptions.MachineSelectionException;
import vendingmachine.exceptions.MachineStockException;

import java.util.List;

public class NotSelectedState implements State {
    private final VendingMachine vendingMachine;

    public NotSelectedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        vendingMachine.setSelectedItem(null);
    }

    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        System.out.println("Please Select an item before inserting coins!");
    }

    @Override
    public void selectItem(String code) throws MachineSelectionException {
        try {
            vendingMachine.getKeypad().enterItemCode(code);
        } catch (Exception e) {
            throw new MachineSelectionException("InvalidCode");
        }
        vendingMachine.setCurrentState(vendingMachine.getItemSelectedState());
    }

    private void printNotSelectedMessage() {
        System.out.println("No item has been selected! Please select an item you wish purchase!");
    }

    @Override
    public Product currentItem() {
        printNotSelectedMessage();
        return null;
    }

    @Override
    public void purchaseItem() throws MachinePurchaseException {
        printNotSelectedMessage();
    }

    @Override
    public List<Product> getBucketProducts() {
        System.out.println("No item has been purchased! Please select an item you wish purchase!");
        return null;
    }

    @Override
    public List<Coin> getBucketCoins() {
        System.out.println("No coins are in the bucket! To proceed, please select an item you wish purchase!");
        return null;
    }

    @Override
    public void login(String username, String password) throws AdminPrivilegeException {
        if (username.equals("OwnerUsername") && password.equals("P4ssw0rd")) {
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }

}
