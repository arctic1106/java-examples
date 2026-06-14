package vendingmachine.states;

import vendingmachine.money.Coin;
import vendingmachine.product.Product;
import vendingmachine.VendingMachine;
import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachineStockException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemPurchasedState implements State {
    private final VendingMachine vendingMachine;

    public ItemPurchasedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCoin(Coin coin, Integer coinAmount) {
        System.out.println("Please collect your money and items from the bucket!");
    }

    @Override
    public double amountDeposited() {
        double depositedAmount = vendingMachine.getRefundCoinStockTotal();
        System.out.println("You have £" + depositedAmount + " still remaining deposited in the machine!");
        return depositedAmount;
    }

    @Override
    public void requestRefund() {
        double refundAmount = Rounding.round(amountDeposited());
        if (refundAmount == 0) {
            System.out
                    .println("You have no coins in the machine to refund. Please collect your items from the bucket!");
        } else {
            if (refundAmount > vendingMachine.getCoinStockTotal()) {
                throw new MachineStockException("CoinStock");
            }
            vendingMachine.calculateChangeDenominations(refundAmount);
            vendingMachine.setCoinBucket();
            System.out.println("Your total change £" + refundAmount
                    + " Total has been placed into the bucket. Please collect your change!");
            vendingMachine.getBucketCoins();
            vendingMachine.getBucketProducts();
            vendingMachine.getInsertedCoinStock().setStock(0);
            vendingMachine.getRefundCoinStock().setStock(0);
        }

    }

    private void printCollectBucketItems() {
        System.out.println("Please collect your money and items from the bucket!");
    }

    @Override
    public void selectItem(String code) {
        printCollectBucketItems();
    }

    @Override
    public Product currentItem() {
        printCollectBucketItems();
        return null;
    }

    @Override
    public void purchaseItem() {
        printCollectBucketItems();
    }

    @Override
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
            vendingMachine.setCurrentState(vendingMachine.getProductEmptyState());
        } else {
            vendingMachine.setCurrentState(vendingMachine.getNotSelectedState());
        }
        return collectedItems;
    }

    @Override
    public List<Coin> getBucketCoins() {
        double changeAmount = vendingMachine.getRefundCoinStockTotal();
        List<Coin> collectedCoins = new ArrayList<>();
        if (changeAmount == 0) {
            System.out.println("The bucket is empty. There are no items to collect from the bucket!");
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
    public void login(String username, String password) {
        if (username.equals("OwnerUsername") && password.equals("P4ssw0rd")) {
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.getAdminModeState().printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }
}
