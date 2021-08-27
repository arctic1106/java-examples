package vendingmachine.states;

import vendingmachine.money.Coin;
import vendingmachine.product.Product;
import vendingmachine.VendingMachine;
import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachinePurchaseException;
import vendingmachine.exceptions.MachineSelectionException;
import vendingmachine.exceptions.MachineStockException;

import java.util.List;

public class ItemSelectedState implements State {
    private final VendingMachine vendingMachine;

    public ItemSelectedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        if (coinAmount < 1) {
            throw new MachineStockException("Coin amount");
        } else {
            vendingMachine.getCoinSlot().coinInserted(coin, coinAmount);
            vendingMachine.setCurrentState(vendingMachine.getCoinInsertedState());
        }
    }

    @Override
    public void selectItem(String code) throws MachineSelectionException {
        try {
            vendingMachine.getKeypad().enterItemCode(code);
        } catch (Exception e) {
            throw new MachineSelectionException("InvalidCode");
        }
    }

    @Override
    public Product currentItem() {
        String currentItem = vendingMachine.getSelectedItem().toString();
        System.out.println("The currently selected item is: " + currentItem);
        System.out.println("The currently selected item: " + currentItem + "'s price is £"
                + vendingMachine.getSelectedItemPrice());
        return vendingMachine.getSelectedItem();
    }

    @Override
    public void purchaseItem() throws MachinePurchaseException {
        System.out
                .println("No money has been inserted. Please insert the money required to purchase the selected item!");
    }

    @Override
    public List<Product> getBucketProducts() {
        System.out.println(
                "No item has been purchased! To proceed, please insert the money required to purchase the selected item!");
        return null;
    }

    @Override
    public List<Coin> getBucketCoins() {
        System.out.println(
                "No coins are in the bucket! To proceed, please insert the money required to purchase the selected item!");
        return null;
    }

    @Override
    public void login(String username, String password) throws AdminPrivilegeException {
        if (username.equals("OwnerUsername") && password.equals("P4ssw0rd")) {
            vendingMachine.setSelectedItem(null);
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }
}
