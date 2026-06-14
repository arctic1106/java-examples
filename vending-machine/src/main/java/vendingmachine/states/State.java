package vendingmachine.states;

import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachinePurchaseException;
import vendingmachine.exceptions.MachineSelectionException;
import vendingmachine.exceptions.MachineStockException;
import vendingmachine.money.Coin;
import vendingmachine.product.Product;

import java.math.BigDecimal;
import java.util.List;

public interface State {
    void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException;

    default BigDecimal amountDeposited() {
        System.out.println("You have not inserted any money!");
        return BigDecimal.ZERO;
    }

    default void requestRefund() {
        throw new MachineStockException("Refund");
    }

    void selectItem(String code);

    Product currentItem();

    void purchaseItem();

    List<Product> getBucketProducts();

    List<Coin> getBucketCoins();

    void login(String username, String password);
}
