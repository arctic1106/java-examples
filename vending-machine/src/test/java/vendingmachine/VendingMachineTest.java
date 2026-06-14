package vendingmachine;

import org.junit.jupiter.api.Test;
import vendingmachine.exceptions.AdminPrivilegeException;
import vendingmachine.exceptions.MachineInitialisationError;
import vendingmachine.exceptions.MachinePurchaseException;
import vendingmachine.exceptions.MachineSelectionException;
import vendingmachine.money.Coin;
import vendingmachine.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
    private static final double DELTA = 1e-15;

    @Test
    public void selectItem() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        assertEquals(vend.getSelectedItem(), Product.COKE);
    }

    @Test
    public void selectItemInvalidCode() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertThrows(MachineSelectionException.class, () -> vend.selectItem("0000"));
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertNull(vend.getSelectedItem());
    }

    @Test
    public void initiateEmptyMachine() {
        VendingMachine vend = new VendingMachine(20, 0, 10);
        assertEquals(vend.getCurrentState(), vend.getProductEmptyState());
        assertEquals(vend.getStock("0001"), 0);
    }

    @Test
    public void initiateInvalidMachine1() {
        assertThrows(MachineInitialisationError.class,
                () -> new VendingMachine(20, -1, 10));
        assertThrows(MachineInitialisationError.class,
                () -> new VendingMachine(20, 10, -1));
        assertThrows(MachineInitialisationError.class,
                () -> new VendingMachine(-1, 10, 10));
        assertThrows(MachineInitialisationError.class,
                () -> new VendingMachine(-1, -2, 10));
    }

    @Test
    public void setState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());

        vend.setCurrentState(vend.getProductEmptyState());
        assertEquals(vend.getCurrentState(), vend.getProductEmptyState());

        vend.setCurrentState(vend.getAdminModeState());
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());

        vend.setCurrentState(vend.getItemPurchasedState());
        assertEquals(vend.getCurrentState(), vend.getItemPurchasedState());

        vend.setCurrentState(vend.getCoinInsertedState());
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());

        vend.setCurrentState(vend.getItemSelectedState());
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
    }

    @Test
    public void getCurrentState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void getItemPurchasedState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.setCurrentState(vend.getItemPurchasedState());
        assertEquals(vend.getCurrentState(), vend.getItemPurchasedState());
    }

    @Test
    public void getCoinInsertedState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.setCurrentState(vend.getCoinInsertedState());
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
    }

    @Test
    public void getItemSelectedState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.setCurrentState(vend.getItemSelectedState());
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
    }

    @Test
    public void getNotSelectedState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void getProductEmptyState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.setCurrentState(vend.getProductEmptyState());
        assertEquals(vend.getCurrentState(), vend.getProductEmptyState());
    }

    @Test
    public void getAdminModeState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.setCurrentState(vend.getAdminModeState());
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());
    }

    @Test
    public void getMAX_SIZE() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getMAX_SIZE(), 20);
    }

    @Test
    public void getProductLevel() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getProductLevel(), 10);
    }

    @Test
    public void getChangeLevel() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getChangeLevel(), 10);
    }

    @Test
    public void getCoinStockTotal() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.getCoinStockTotal(), 42.88, DELTA);
    }

    @Test
    public void getInsertedCoinStockTotal() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.getInsertedCoinStockTotal(), 4.08, DELTA);
    }

    @Test
    public void getRefundCoinStockTotal() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.getRefundCoinStock().setStock(2);
        assertEquals(vend.getRefundCoinStockTotal(), 7.76, DELTA);
    }

    @Test
    public void getCoinBucketTotal() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCoinBucketTotal(), 0, DELTA);
        vend.getRefundCoinStock().setStock(2);
        vend.setCoinBucket();
        assertEquals(vend.getCoinBucketTotal(), 7.76, DELTA);
    }

    @Test
    public void setCoinBucket() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCoinBucketTotal(), 0, DELTA);
        vend.getRefundCoinStock().setStock(2);
        vend.setCoinBucket();
        assertEquals(vend.getCoinBucketTotal(), 7.76, DELTA);
    }

    @Test
    public void setSelectedItem() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertNull(vend.getSelectedItem());
        vend.setSelectedItem(Product.COKE);
        assertEquals(vend.getSelectedItem(), Product.COKE);
        vend.setSelectedItem(Product.CANDY);
        assertEquals(vend.getSelectedItem(), Product.CANDY);
    }

    @Test
    public void getSelectedItem() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertNull(vend.getSelectedItem());
        vend.selectItem("0001");
        assertEquals(vend.getSelectedItem(), Product.COKE);
        vend.selectItem("1004");
        assertEquals(vend.getSelectedItem(), Product.CANDY);
    }

    @Test
    public void insertCoinCorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.amountDeposited(), 4.08, DELTA);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
    }

    @Test
    public void insertCoinIncorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 3);
        assertEquals(vend.amountDeposited(), 0, DELTA);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void printAdminCoinInfoCorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.login("OwnerUsername", "P4ssw0rd");
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());
        double totalMachineMoney = vend.getCoinStockTotal();
        StringBuilder str = new StringBuilder();
        str.append("\n |--------------------------------------");
        str.append("\n | \t Printing Coin Stock information");
        str.append("\n |--------------------------------------");
        str.append("\n | \t Total money in machine = £").append(totalMachineMoney);
        str.append("\n |--------------------------------------");
        for (Map.Entry<Coin, Integer> entry : vend.getCoinStock().entrySet()) {
            Coin coin = entry.getKey();
            int value = vend.getCoinStock().get(coin);
            str.append("\n |\t Coin: ").append(coin).append(" Amount: ").append(value);
        }
        str.append("\n |--------------------------------------");

        assertEquals(vend.printAdminCoinInfo(), str.toString());
    }

    @Test
    public void printAdminCoinInfoIncorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(AdminPrivilegeException.class, vend::printAdminCoinInfo);
    }

    @Test
    public void printAdminProductInfoCorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.login("OwnerUsername", "P4ssw0rd");
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());
        StringBuilder str1 = new StringBuilder();
        str1.append("\n |--------------------------------------");
        str1.append("\n | \t Printing Product Stock information");
        str1.append("\n |--------------------------------------");
        for (Map.Entry<Enum, Integer> entry : vend.getProductStock().entrySet()) {
            Product product = (Product) entry.getKey();
            int value = vend.getProductStock().get(product);
            str1.append("\n |\t Product: ").append(product).append(" Amount: ").append(value);
        }
        str1.append("\n |--------------------------------------");

        assertEquals(vend.printAdminProductInfo(), str1.toString());
    }

    @Test
    public void printAdminProductInfoIncorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(AdminPrivilegeException.class, vend::printAdminProductInfo);
    }

    @Test
    public void amountDeposited() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        assertEquals(vend.amountDeposited(), 0, DELTA);
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.amountDeposited(), 4.08, DELTA);
        vend.requestRefund();
        assertEquals(vend.amountDeposited(), 0, DELTA);
    }

    @Test
    public void requestRefund() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        assertEquals(vend.amountDeposited(), 0, DELTA);
        vend.requestRefund();
        assertEquals(vend.amountDeposited(), 0, DELTA);
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.amountDeposited(), 4.08, DELTA);
        assertEquals(vend.getInsertedCoinStockTotal(), 4.08, DELTA);
        vend.requestRefund();
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(vend.amountDeposited(), 0, DELTA);
        assertEquals(vend.getInsertedCoinStockTotal(), 0, DELTA);

        // Check Collected Change is correct Change returned is 4.08
        // 4.08 change split into the highest change denominations available
        List<Coin> testCoins = new ArrayList<>();
        testCoins.add(Coin.TWO_POUND); // 2 +
        testCoins.add(Coin.TWO_POUND); // 2 +
        testCoins.add(Coin.FIVE_PENCE); // 0.05 +
        testCoins.add(Coin.TWO_PENCE); // 0.02 +
        testCoins.add(Coin.ONE_PENCE); // 0.01 = 4.08
        assertEquals(testCoins, vend.getCollectedCoins());
    }

    @Test
    public void isMachineEmptyCheck() {
        VendingMachine vend = new VendingMachine(20, 0, 10);
        assertTrue(vend.isMachineEmptyCheck());
        VendingMachine vend2 = new VendingMachine(20, 1, 10);
        assertFalse(vend2.isMachineEmptyCheck());
    }

    @Test
    public void testSelectItemCorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertNull(vend.getSelectedItem());
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        assertEquals(vend.getSelectedItem(), Product.COKE);
        vend.selectItem("0002");
        assertEquals(vend.getSelectedItem(), Product.SPRITE);
    }

    @Test
    public void testSelectItemIncorrectState() {
        VendingMachine vend = new VendingMachine(20, 0, 10);
        assertNull(vend.getSelectedItem());
        assertEquals(vend.getCurrentState(), vend.getProductEmptyState());
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getProductEmptyState());
        assertNull(vend.getSelectedItem());
        vend.selectItem("0002");
        assertNull(vend.getSelectedItem());
    }

    @Test
    public void testSelectItemIncorrectCode() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertNull(vend.getSelectedItem());
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(MachineSelectionException.class, () -> vend.selectItem("01"));
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertNull(vend.getSelectedItem());
    }

    @Test
    public void currentItemCorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.currentItem(), Product.COKE);
        vend.selectItem("0002");
        assertEquals(vend.currentItem(), Product.SPRITE);
        vend.insertCoin(Coin.ONE_POUND, 3);
        assertEquals(vend.currentItem(), Product.SPRITE);
    }

    @Test
    public void currentItemWhenNull() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(MachineSelectionException.class, vend::currentItem);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void getSelectedItemPriceCorrect() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.currentItem(), Product.COKE);
        assertEquals(vend.getSelectedItemPrice(), 2.00, DELTA);
    }

    @Test
    public void getSelectedItemPriceWhenNull() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertThrows(MachineSelectionException.class, vend::getSelectedItemPrice);
    }

    @Test
    public void purchaseItemCorrectState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        vend.purchaseItem();
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());

        List<Product> testProducts = new ArrayList<>();
        testProducts.add(Product.COKE);
        assertEquals(testProducts, vend.getCollectedProducts());

        // Check Collected Change is correct Change returned is 4.08-2
        // 2.08 change split into the highest change denominations available
        List<Coin> testCoins = new ArrayList<>();
        testCoins.add(Coin.TWO_POUND); // 2 +
        testCoins.add(Coin.FIVE_PENCE); // 0.05 +
        testCoins.add(Coin.TWO_PENCE); // 0.02 +
        testCoins.add(Coin.ONE_PENCE); // 0.01 = 2.08
        assertEquals(testCoins, vend.getCollectedCoins());

    }

    @Test
    public void purchaseItemOutOfStock() {
        VendingMachine vend = new VendingMachine(20, 1, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 2);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        vend.purchaseItem();
        assertEquals(vend.getCurrentState(), vend.getItemPurchasedState());
        vend.getBucketProducts();
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 2);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        assertThrows(MachinePurchaseException.class, vend::purchaseItem);
    }

    @Test
    public void purchaseItemNotEnoughMoney() {
        VendingMachine vend = new VendingMachine(20, 1, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 1);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        assertThrows(MachinePurchaseException.class, vend::purchaseItem);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        vend.insertCoin(Coin.ONE_POUND, 1);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        vend.purchaseItem();
        assertEquals(vend.getCurrentState(), vend.getItemPurchasedState());
    }

    @Test
    public void getBucketProducts() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(Product.COKE);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        assertEquals(vend.getSelectedItem(), Product.COKE);
        vend.purchaseItem();
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(testProducts, vend.getCollectedProducts());
    }

    @Test
    public void getBucketCoins() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        List<Coin> testCoins = new ArrayList<>();
        testCoins.add(Coin.ONE_POUND);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 1);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        vend.requestRefund();
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(testCoins, vend.getCollectedCoins());
    }

    @Test
    public void getBucketCoinsDifferentCoinValues() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        List<Coin> testCoins = new ArrayList<>();
        testCoins.add(Coin.TWO_POUND);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 2);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        vend.requestRefund();
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(testCoins, vend.getCollectedCoins());
    }

    @Test
    public void getStock() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        vend.selectItem("0001");
        assertEquals(vend.getCurrentState(), vend.getItemSelectedState());
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        assertEquals(vend.getCurrentState(), vend.getCoinInsertedState());
        assertEquals(vend.getStock("0001"), 10);
        vend.purchaseItem();
        assertEquals(vend.getStock("0001"), 9);
    }

    @Test
    public void getStockInvalidCode() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertThrows(MachineSelectionException.class, () -> vend.getStock("001"));
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void login() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.login("OwnerUsername", "P4ssw0rd");
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());
    }

    @Test
    public void loginIncorrectDetails() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(AdminPrivilegeException.class, () -> vend.login("OwnerUsername", "WrongPassword"));
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void logout() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.login("OwnerUsername", "P4ssw0rd");
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());
        vend.logout();
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void logoutNotAdminState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(AdminPrivilegeException.class, vend::logout);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void depositCoins() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        vend.login("OwnerUsername", "P4ssw0rd");
        assertEquals(vend.getCurrentState(), vend.getAdminModeState());
        assertEquals(vend.getCoinStockTotal(), 38.8, DELTA);
        vend.depositCoins(10);
        assertEquals(vend.getCoinStockTotal(), 77.6, DELTA);
    }

    @Test
    public void depositCoinsNotAdminState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(vend.getCoinStockTotal(), 38.8, DELTA);
        assertThrows(AdminPrivilegeException.class, () -> vend.depositCoins(10));
        assertEquals(vend.getCoinStockTotal(), 38.8, DELTA);
    }

    @Test
    public void withdrawCoins() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(vend.getCoinStockTotal(), 38.8, DELTA);
        vend.login("OwnerUsername", "P4ssw0rd");
        vend.withdrawCoins();
        assertEquals(vend.getCoinStockTotal(), 0, DELTA);
    }

    @Test
    public void withdrawCoinsNotAdminState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(vend.getCoinStockTotal(), 38.8, DELTA);
        assertThrows(AdminPrivilegeException.class, vend::withdrawCoins);
        assertEquals(vend.getCoinStockTotal(), 38.8, DELTA);
    }

    @Test
    public void adminRefillProduct() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(vend.getStock("0001"), 10);
        vend.login("OwnerUsername", "P4ssw0rd");
        vend.adminRefillProduct(Product.COKE);
        assertEquals(vend.getStock("0001"), vend.getMAX_SIZE());
    }

    @Test
    public void adminRefillProductNotAdminState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(AdminPrivilegeException.class, () -> vend.adminRefillProduct(Product.COKE));
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }

    @Test
    public void adminRefillAllProduct() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertEquals(vend.getStock("0001"), 10);
        assertEquals(vend.getStock("0002"), 10);
        vend.login("OwnerUsername", "P4ssw0rd");
        vend.adminRefillAllProduct();
        assertEquals(vend.getStock("0001"), vend.getMAX_SIZE());
        assertEquals(vend.getStock("0002"), vend.getMAX_SIZE());
    }

    @Test
    public void adminRefillAllProductNotAdminState() {
        VendingMachine vend = new VendingMachine(20, 10, 10);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
        assertThrows(AdminPrivilegeException.class, vend::adminRefillAllProduct);
        assertEquals(vend.getCurrentState(), vend.getNotSelectedState());
    }
}
