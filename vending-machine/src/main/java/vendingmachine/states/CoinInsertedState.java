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

public class CoinInsertedState implements State {
    private final VendingMachine vendingMachine;

    public CoinInsertedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        if (coinAmount < 1) {
            throw new MachineStockException("Coin amount");
        } else {
            vendingMachine.getCoinSlot().coinInserted(coin, coinAmount);
        }
    }

    @Override
    public double amountDeposited() {
        double total = vendingMachine.getInsertedCoinStockTotal();
        return Rounding.round(total);
    }

    @Override
    public void requestRefund() {
        double refundAmount = Rounding.round(amountDeposited());
        if (refundAmount == 0) {
            System.out.println("You have not inserted any coins!");
        } else {
            if (refundAmount > vendingMachine.getCoinStockTotal()) {
                throw new MachineStockException("CoinStock");
            }
            vendingMachine.calculateChangeDenominations(refundAmount);
            vendingMachine.setCoinBucket();
            System.out.println("Your total change £" + refundAmount
                    + " Total has been placed into the bucket. Please collect your change!");
            vendingMachine.getInsertedCoinStock().setStock(0);
        }
        vendingMachine.getBucketCoins();
        System.out.println("Your order has been cancelled. Please select an item to proceed!");
        vendingMachine.setCurrentState(vendingMachine.getNotSelectedState());
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
        Product selectedItem = vendingMachine.getSelectedItem();
        if (vendingMachine.getSelectedItemPrice() > amountDeposited()) {
            System.out.println("You have not inserted enough money to purchase this item. You have inserted £"
                    + amountDeposited());
            System.out.println("The currently selected item: " + selectedItem.toString() + "'s price is £"
                    + vendingMachine.getSelectedItemPrice());
            throw new MachinePurchaseException("money");
        } else {
            int itemStock = vendingMachine.getProductStock().get(selectedItem);
            if (itemStock == 0) {
                throw new MachinePurchaseException("stock");
            } else {
                double selectedItemPrice = vendingMachine.getSelectedItemPrice();
                double afterPurchaseRefundAmount = Rounding
                        .round(vendingMachine.getInsertedCoinStockTotal() - selectedItemPrice);
                vendingMachine.calculateChangeDenominations(afterPurchaseRefundAmount);
                System.out.println(selectedItem.toString() + " was purchased!");
                vendingMachine.setSelectedItem(null);
                addPurchasedProductToBucket(selectedItem);
                vendingMachine.getProductStock().reduce(selectedItem);
                vendingMachine.setCurrentState(vendingMachine.getItemPurchasedState());
            }
        }
    }

    private void addPurchasedProductToBucket(Product purchasedProduct) {
        System.out.println("Your purchased " + purchasedProduct.toString()
                + " dropped into the bucket. Please collect your purchased item!");
        vendingMachine.getProductBucket().insert(purchasedProduct);
    }

    @Override
    public List<Product> getBucketProducts() {
        System.out.println("No item has been purchased! Please purchase an item to proceed.");
        return null;
    }

    @Override
    public List<Coin> getBucketCoins() {
        double changeAmount = vendingMachine.getRefundCoinStockTotal();
        List<Coin> collectedCoins = new ArrayList<>();
        if (changeAmount == 0) {
            System.out.println("The bucket is empty. There are no coins to collect from the bucket!");
        } else {
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinBucket().entrySet()) {
                if (entry.getValue() > 0) {
                    for (int i = 0; entry.getValue() > i; i++) {
                        collectedCoins.add(entry.getKey());
                    }
                }
            }
            System.out
                    .println("You collected your £" + Rounding.round(changeAmount) + " total change from the bucket!");
            vendingMachine.getRefundCoinStock().setStock(0);
            vendingMachine.getCoinBucket().setStock(0);
        }
        return collectedCoins;
    }

    @Override
    public void login(String username, String password) throws AdminPrivilegeException {
        if (username.equals("OwnerUsername") && password.equals("P4ssw0rd")) {
            vendingMachine.requestRefund();
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }
}
