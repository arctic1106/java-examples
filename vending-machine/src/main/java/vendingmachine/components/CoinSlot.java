package vendingmachine.components;

import vendingmachine.money.Coin;
import vendingmachine.VendingMachine;

public class CoinSlot {
    private final VendingMachine vendingMachine;

    public CoinSlot(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void coinInserted(Coin coin, Integer coinAmount) {
        int coinStockLevel = vendingMachine.getCoinStock().get(coin);
        coinStockLevel = coinStockLevel + coinAmount;
        int insertedCoinLevel = vendingMachine.getInsertedCoinStock().get(coin);
        insertedCoinLevel = insertedCoinLevel + coinAmount;
        vendingMachine.getCoinStock().put(coin, coinStockLevel);
        vendingMachine.getInsertedCoinStock().put(coin, insertedCoinLevel);
        System.out.println("You inserted " + coinAmount + " " + coin.toString() + " coins");
    }
}
