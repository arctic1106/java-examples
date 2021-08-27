package vendingmachine.states;

import vendingmachine.money.Coin;
import vendingmachine.product.Product;
import vendingmachine.VendingMachine;
import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachinePurchaseException;
import vendingmachine.exceptions.MachineSelectionException;
import vendingmachine.exceptions.MachineStockException;

import java.util.List;

public class ProductEmptyState implements State {
    private final VendingMachine vendingMachine;

    public ProductEmptyState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    private void printMachineEmptyMessage() {
        System.out.println("Machine is out of all stock. Please contact the Administrator of the machine!");
    }

    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        printMachineEmptyMessage();
    }

    @Override
    public double amountDeposited() {
        printMachineEmptyMessage();
        return 0;
    }

    @Override
    public void requestRefund() {
        printMachineEmptyMessage();
    }

    @Override
    public void selectItem(String code) throws MachineSelectionException {
        printMachineEmptyMessage();
    }

    @Override
    public Product currentItem() {
        printMachineEmptyMessage();
        return null;
    }

    @Override
    public void purchaseItem() throws MachinePurchaseException {
        printMachineEmptyMessage();
    }

    @Override
    public List<Product> getBucketProducts() {
        printMachineEmptyMessage();
        return null;
    }

    @Override
    public List<Coin> getBucketCoins() {
        printMachineEmptyMessage();
        return null;
    }

    @Override
    public void login(String username, String password) throws AdminPrivilegeException {
        if ("OwnerUsername".equals(username) && "P4ssw0rd".equals(password)) {
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }
}
