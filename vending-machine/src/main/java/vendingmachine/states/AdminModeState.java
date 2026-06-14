package vendingmachine.states;

import vendingmachine.money.Coin;
import vendingmachine.product.Product;
import vendingmachine.VendingMachine;
import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachinePurchaseException;
import vendingmachine.exceptions.MachineSelectionException;
import vendingmachine.exceptions.MachineStockException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminModeState {
    private final VendingMachine vendingMachine;

    public AdminModeState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        if (coinAmount < 1) {
            throw new MachineStockException("Coin amount");
        } else {
            int coinStockLevel = vendingMachine.getCoinStock().get(coin);
            coinStockLevel = coinStockLevel + coinAmount;
            vendingMachine.getCoinStock().put(coin, coinStockLevel);
            System.out.println("Admin inserted " + coinAmount + " " + coin.toString() + " coins into the machine!");
        }
    }

    public double amountDeposited() {
        this.printAdminCoinInfo();
        return vendingMachine.getCoinStockTotal();
    }

    public void requestRefund() {
        System.out.println("Currently in Admin Mode. To Access this functionality Logout!");
    }

    public void selectItem(String code) throws MachineSelectionException {
        try {
            vendingMachine.getKeypad().enterItemCode(code);
        } catch (Exception e) {
            throw new MachineSelectionException("InvalidCode");
        }
    }

    public Product currentItem() {
        String currentItem = vendingMachine.getSelectedItem().toString();
        System.out.println("The currently selected item is: " + currentItem);
        System.out.println("The currently selected item: " + currentItem + "'s price is £"
                + vendingMachine.getSelectedItemPrice());
        return vendingMachine.getSelectedItem();
    }

    public void purchaseItem() throws MachinePurchaseException {
        Product selectedItem = vendingMachine.getSelectedItem();
        int itemStock = vendingMachine.getProductStock().get(selectedItem);
        if (itemStock == 0) {
            String errorCode = "stock";
            throw new MachinePurchaseException(errorCode);
        } else {
            System.out
                    .println("Admin retrieved one " + selectedItem.toString() + "! The item dropped into the bucket.");
            vendingMachine.setSelectedItem(null);
            vendingMachine.getProductBucket().insert(selectedItem);
            vendingMachine.getProductStock().reduce(selectedItem);
        }
    }

    public List<Product> getBucketProducts() {
        int size = vendingMachine.getProductBucket().size();
        int count = 0;
        List<Product> collectedItems = new ArrayList<>();
        for (Map.Entry<Enum, Integer> entry : vendingMachine.getProductBucket().entrySet()) {
            count++;
            if (entry.getValue() > 0) {
                collectedItems.add((Product) entry.getKey());
                System.out.println("You collected your " + entry.getKey().toString() + " from the bucket!");
            } else if (size == count - 1) {
                System.out.println("The bucket is empty. There are no items to collect from the bucket!");

            }
        }
        vendingMachine.getProductBucket().setStock(0);
        if (vendingMachine.isMachineEmptyCheck()) {
            System.out.println("The machine is empty. Please refill the machine!");
        } else {
            this.printAdminProductInfo();
        }
        return collectedItems;
    }

    public List<Coin> getBucketCoins() {
        double changeAmount = vendingMachine.getRefundCoinStockTotal();
        List<Coin> collectedCoins = new ArrayList<>();
        if (changeAmount == 0) {
            System.out.println("The bucket is empty. There are no coins to collect from the bucket!");
        } else {
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinBucket().entrySet()) {
                if (entry.getValue() > 0) {
                    collectedCoins.add(entry.getKey());
                }
            }
            System.out.println(
                    "You collected your £" + Rounding.round(changeAmount) + " total withdrawn money from the bucket!");
            vendingMachine.getRefundCoinStock().setStock(0);
            vendingMachine.getCoinBucket().setStock(0);
        }
        return collectedCoins;
    }

    public void login(String username, String password) {
        System.out.println("You are already logged in to Admin!");
    }

    public void logout() {
        System.out.println("Logged out of Admin!");
        if (vendingMachine.isMachineEmptyCheck()) {
            vendingMachine.setCurrentState(vendingMachine.getProductEmptyState());
        } else {
            vendingMachine.setCurrentState(vendingMachine.getNotSelectedState());
        }
    }

    public List<Coin> withdrawCoins() {
        double refundAmount = Rounding.round(vendingMachine.getCoinStockTotal());

        vendingMachine.calculateChangeDenominations(refundAmount);
        vendingMachine.setCoinBucket();
        System.out.println("Your total change £" + refundAmount
                + " Total has been placed into the bucket. Please collect your withdrawn coins!");
        vendingMachine.getInsertedCoinStock().setStock(0);
        return vendingMachine.getBucketCoins();
    }

    public void depositCoins(Integer amount) throws MachineStockException {
        if (amount > 0) {
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinStock().entrySet()) {
                vendingMachine.insertCoin(entry.getKey(), amount);
            }
        } else {
            throw new MachineStockException("Inserted amount of coins must be more than 0");
        }
    }

    public void adminRefillProduct(Product product) {
        int machineMaxSize = vendingMachine.getMAX_SIZE();
        int currentStockLevel = vendingMachine.getProductStock().get(product);
        if (machineMaxSize > currentStockLevel) {
            vendingMachine.getProductStock().put(product, machineMaxSize);
            System.out.println("The Vending Machine was fully refilled of " + product.toString() + "!");
        } else {
            System.out.println("The Vending Machine's max stock size is: " + machineMaxSize
                    + ". The Vending Machine is already full of " + product.toString() + ".");
        }
    }

    public void adminRefillAllProduct() {
        int machineMaxSize = vendingMachine.getMAX_SIZE();
        vendingMachine.getProductStock().setStock(machineMaxSize);
        System.out.println("The Vending Machine was fully refilled!");
    }

    public String printAdminCoinInfo() {
        if (vendingMachine.getCurrentState() == vendingMachine.getAdminModeState()) {
            double totalMachineMoney = vendingMachine.getCoinStockTotal();
            StringBuilder str = new StringBuilder();
            str.append("\n |--------------------------------------");
            str.append("\n | \t Printing Coin Stock information");
            str.append("\n |--------------------------------------");
            str.append("\n | \t Total money in machine = £").append(totalMachineMoney);
            str.append("\n |--------------------------------------");
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinStock().entrySet()) {
                Coin coin = entry.getKey();
                int value = vendingMachine.getCoinStock().get(coin);
                str.append("\n |\t Coin: ").append(coin).append(" Amount: ").append(value);
            }
            str.append("\n |--------------------------------------");
            System.out.println(str);
            return str.toString();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    public String printAdminProductInfo() {
        if (vendingMachine.getCurrentState() == vendingMachine.getAdminModeState()) {
            StringBuilder str1 = new StringBuilder();
            str1.append("\n |--------------------------------------");
            str1.append("\n | \t Printing Product Stock information");
            str1.append("\n |--------------------------------------");
            for (Map.Entry<Enum, Integer> entry : vendingMachine.getProductStock().entrySet()) {
                Product product = (Product) entry.getKey();
                int value = vendingMachine.getProductStock().get(product);
                str1.append("\n |\t Product: ").append(product).append(" Amount: ").append(value);
            }
            str1.append("\n |--------------------------------------");
            System.out.println(str1);
            return str1.toString();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    public void printAdminInfo() {
        if (vendingMachine.getCurrentState() == vendingMachine.getAdminModeState()) {
            printAdminProductInfo();
            printAdminCoinInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }
}
