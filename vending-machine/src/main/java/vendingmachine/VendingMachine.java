package vendingmachine;

import vendingmachine.components.CoinSlot;
import vendingmachine.components.Keypad;
import vendingmachine.exceptions.*;
import vendingmachine.money.Coin;
import vendingmachine.money.CoinStock;
import vendingmachine.product.Product;
import vendingmachine.product.ProductStock;
import vendingmachine.states.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VendingMachine {
    private final CoinSlot coinSlot;
    private final Keypad keypad;
    private final State itemPurchasedState;
    private final State itemSelectedState;
    private final State notSelectedState;
    private final State coinInsertedState;
    private final State productEmptyState;
    private final AdminModeState adminModeState;
    private final int MAX_SIZE;
    private final int productLevel;
    private final int changeLevel;
    private final CoinStock coinStock = new CoinStock();
    private final CoinStock insertedCoinStock = new CoinStock();
    private final CoinStock refundCoinStock = new CoinStock();
    private final CoinStock coinBucket = new CoinStock();
    private final ProductStock productStock = new ProductStock();
    private final ProductStock productBucket = new ProductStock();
    private State currentState;
    private Product selectedItem;
    private String selectedItemCode;
    private List<Product> collectedProducts = new ArrayList<>();
    private List<Coin> collectedCoins = new ArrayList<>();

    public VendingMachine(int MAX_SIZE, int productLevel, int changeLevel) throws MachineInitialisationError {
        this.MAX_SIZE = MAX_SIZE;
        this.productLevel = productLevel;
        this.changeLevel = changeLevel;
        this.coinSlot = new CoinSlot(this);
        this.keypad = new Keypad(this);

        if (MAX_SIZE < 1) {
            throw new MachineInitialisationError("Size");
        } else if (productLevel < 0) {
            throw new MachineInitialisationError("Product");
        } else if (MAX_SIZE < productLevel) {
            throw new MachineInitialisationError("ProductMax");
        } else if (changeLevel < 0) {
            throw new MachineInitialisationError("Change");
        } else {
            coinStock.setStock(changeLevel);
            productStock.setStock(productLevel);
        }

        itemPurchasedState = new ItemPurchasedState(this);
        coinInsertedState = new CoinInsertedState(this);
        itemSelectedState = new ItemSelectedState(this);
        notSelectedState = new NotSelectedState(this);
        productEmptyState = new ProductEmptyState(this);
        adminModeState = new AdminModeState(this);

        if (productLevel > 0) {
            currentState = notSelectedState;
        } else {
            currentState = productEmptyState;
        }

    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public State getItemPurchasedState() {
        return itemPurchasedState;
    }

    public State getCoinInsertedState() {
        return coinInsertedState;
    }

    public State getItemSelectedState() {
        return itemSelectedState;
    }

    public State getNotSelectedState() {
        setSelectedItem(null);
        setSelectedItemCode(null);
        return notSelectedState;
    }

    public State getProductEmptyState() {
        System.out.println("The machine is empty! All stock has been sold!");
        return productEmptyState;
    }

    public AdminModeState getAdminModeState() {
        return adminModeState;
    }

    public CoinSlot getCoinSlot() {
        return this.coinSlot;
    }

    public Keypad getKeypad() {
        return this.keypad;
    }

    public int getMAX_SIZE() {
        return this.MAX_SIZE;
    }

    int getProductLevel() {
        return this.productLevel;
    }

    int getChangeLevel() {
        return this.changeLevel;
    }

    public CoinStock getCoinStock() {
        return coinStock;
    }

    public CoinStock getInsertedCoinStock() {
        return insertedCoinStock;
    }

    public CoinStock getRefundCoinStock() {
        return refundCoinStock;
    }

    public ProductStock getProductStock() {
        return productStock;
    }

    public ProductStock getProductBucket() {
        return productBucket;
    }

    public CoinStock getCoinBucket() {
        return coinBucket;
    }

    public BigDecimal getCoinStockTotal() {
        return coinStock.getMachineTotalMoneyValue();
    }

    public BigDecimal getInsertedCoinStockTotal() {
        return insertedCoinStock.getMachineTotalMoneyValue();
    }

    public BigDecimal getRefundCoinStockTotal() {
        return refundCoinStock.getMachineTotalMoneyValue();
    }

    public BigDecimal getCoinBucketTotal() {
        return coinBucket.getMachineTotalMoneyValue();
    }

    public void setCoinBucket() {
        for (Map.Entry entry : refundCoinStock.entrySet()) {
            Coin coin = entry.getKey();
            int value = refundCoinStock.get(coin);
            coinBucket.put(coin, value);
        }
    }

    public void setSelectedItemCode(String code) {
        this.selectedItemCode = code;
    }

    public Product getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Product selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        currentState.insertCoin(coin, coinAmount);
        if (getInsertedCoinStockTotal() > 0) {
            System.out.println("Total money inserted = £" + amountDeposited());
        }
    }

    public BigDecimal amountDeposited() {
        return currentState.amountDeposited();
    }

    public void requestRefund() {
        currentState.requestRefund();
    }

    public boolean isMachineEmptyCheck() {
        for (Map.Entry<Enum, Integer> entry : productStock.entrySet()) {
            if (productStock.get((Product) entry.getKey()) > 0) {
                return false;
            }
        }
        return true;
    }

    public CoinStock calculateChangeDenominations(BigDecimal amount) {
        var refundAmount = amount;
        for (var entry : coinStock.entrySet()) {
            if (refundAmount.equals(BigDecimal.ZERO)) {
                break;
            } else {
                Coin coin = entry.getKey();
                var coinValue = coin.value();
                int coinStockLevel = entry.getValue();
                int coinMultiplier = refundAmount / coinValue;
                if (coinMultiplier == 0) {
                    continue;
                } else {
                    double remainder = (refundAmount % coinValue);
                    String coinString = coin.toString();
                    if (coinMultiplier <= coinStockLevel) {
                        refundCoinStock.put(coin, coinMultiplier);
                        System.out.println(coinMultiplier + " " + coinString + " coin was placed in the bucket");
                        coinStock.put(coin, coinStockLevel - coinMultiplier);
                        refundAmount = refundAmount - (coinValue * coinMultiplier);
                    } else {
                        coinMultiplier = coinStockLevel;
                        refundCoinStock.put(coin, coinMultiplier);
                        System.out.println(coinMultiplier + " " + coinString + " coin was placed in the bucket");
                        coinStock.put(coin, coinStockLevel - coinMultiplier);
                        refundAmount = refundAmount - (coinValue * coinMultiplier);
                    }
                }
            }
        }
        return refundCoinStock;
    }

    public void selectItem(String code) throws MachineSelectionException {
        currentState.selectItem(code);
    }

    public Product currentItem() throws MachineSelectionException {
        if (this.selectedItem == null) {
            throw new MachineSelectionException("noItemSelected");
        }
        return currentState.currentItem();
    }

    public BigDecimal getSelectedItemPrice() throws MachineSelectionException {
        if (this.selectedItem == null) {
            throw new MachineSelectionException("noItemSelected");
        } else {
            return Product.getProductByCode(selectedItemCode).getPrice();
        }
    }

    public void purchaseItem() throws MachinePurchaseException {
        currentState.purchaseItem();
        if (getCurrentState() == getItemPurchasedState()) {
            requestRefund();
        }
    }

    public List<Product> getBucketProducts() {
        List<Product> products = currentState.getBucketProducts();
        if (products != null) {
            this.collectedProducts = products;
        }
        return this.collectedProducts;
    }

    List<Product> getCollectedProducts() {
        try {
            return this.collectedProducts;
        } catch (Exception e) {
            System.out.println("Collected Products is null");
        }
        return null;
    }

    public List<Coin> getBucketCoins() {
        List<Coin> coins = currentState.getBucketCoins();
        if (coins != null) {
            this.collectedCoins = coins;
        }
        return this.collectedCoins;
    }

    List<Coin> getCollectedCoins() {
        try {
            return this.collectedCoins;
        } catch (Exception e) {
            System.out.println("Collected Coins is null");
        }
        return null;
    }

    int getStock(String code) throws MachineSelectionException {
        try {
            var product = Product.getProductByCode(code);
            int stockLevel = getProductStock().get(product);
            System.out.println("Their are " + stockLevel + " remaining "
                    + Objects.requireNonNull(product) + " in the machine!");
            return stockLevel;
        } catch (Exception e) {
            throw new MachineSelectionException("InvalidCode");
        }
    }

    public void login(String username, String password) throws AdminPrivilegeException {
        currentState.login(username, password);
    }

    public void logout() throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            adminModeState.logout();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to Logout of Admin Mode!");
        }
    }

    public String printAdminCoinInfo() throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            return adminModeState.printAdminCoinInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }

    }

    public String printAdminProductInfo() throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            return adminModeState.printAdminProductInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    public void printAdminInfo() throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            adminModeState.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    public List<Coin> withdrawCoins() {
        if (getCurrentState() == getAdminModeState()) {
            return adminModeState.withdrawCoins();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to withdraw coins from the machine!");
        }
    }

    public void depositCoins(Integer amount) throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            adminModeState.depositCoins(amount);
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to deposit coins into the machine!");
        }
    }

    public void adminRefillProduct(Product product) throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            adminModeState.adminRefillProduct(product);
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to refill the Vending Machine!");
        }
    }

    public void adminRefillAllProduct() throws AdminPrivilegeException {
        if (getCurrentState() == getAdminModeState()) {
            adminModeState.adminRefillAllProduct();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to refill the Vending Machine!");
        }
    }
}
