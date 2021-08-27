package vendingmachine;

import vendingmachine.money.Coin;
import vendingmachine.product.Product;

public class VendingMachineMain {

    public static void main(String[] args) {
        var vendingMachine = new VendingMachine(20, 10, 10);
        vendingMachine.selectItem("0004");
        vendingMachine.currentItem();
        vendingMachine.amountDeposited();

        vendingMachine.insertCoin(Coin.ONE_POUND, 3);
        vendingMachine.insertCoin(Coin.FIFTY_PENCE, 2);
        vendingMachine.insertCoin(Coin.TWO_PENCE, 2);
        vendingMachine.insertCoin(Coin.ONE_PENCE, 4);

        vendingMachine.requestRefund();

        vendingMachine.selectItem("1002");
        vendingMachine.insertCoin(Coin.ONE_POUND, 3);
        vendingMachine.purchaseItem();

        vendingMachine.login("OwnerUsername", "P4ssw0rd");
        vendingMachine.adminRefillProduct(Product.COKE);
        vendingMachine.printAdminProductInfo();
        vendingMachine.adminRefillAllProduct();
        vendingMachine.printAdminProductInfo();

        vendingMachine.depositCoins(50);
        vendingMachine.printAdminCoinInfo();

        vendingMachine.withdrawCoins();
        vendingMachine.printAdminCoinInfo();

        vendingMachine.depositCoins(50);
        vendingMachine.printAdminCoinInfo();

        vendingMachine.logout();

        vendingMachine.selectItem("0003");
        vendingMachine.currentItem();
        vendingMachine.insertCoin(Coin.ONE_POUND, 3);
        vendingMachine.insertCoin(Coin.FIFTY_PENCE, 2);
        vendingMachine.insertCoin(Coin.TWO_PENCE, 2);
        vendingMachine.insertCoin(Coin.ONE_PENCE, 4);
        vendingMachine.amountDeposited();
        vendingMachine.getStock("0003");
        vendingMachine.purchaseItem();
        vendingMachine.getStock("0003");
        System.out.println("Collect Change = " + vendingMachine.getCollectedCoins());
        System.out.println("Collect Item = " + vendingMachine.getCollectedProducts());
    }
}
