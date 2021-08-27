package vendingmachine.exceptions;

public final class ErrorMessages {
    public static final String INVALID_CODE = "Invalid code entered!";
    public static final String REFUND = "You have not entered any money to be refunded!";
    public static final String SIZE = "Vending Machine MAX_SIZE must be greater than 0!";
    public static final String PRODUCT = "Vending Machine Product Level must be a positive number!";
    public static final String CHANGE = "Vending Machine Change Level must be a positive number!";
    public static final String PRODUCT_MAX = "Vending Machine Product Level must be less than the max size of the Vending Machine!";
    public static final String STOCK = "The item you attempted to purchase is out of stock!";
    public static final String MONEY = "You do not have the required funds!";
    public static final String NO_ITEM_SELECTED = "No item selected to purchase!";
    public static final String COIN_AMOUNT = "You cannot enter negative amounts of coins, please enter a positive value!";
    public static final String COIN_STOCK = "The machine does not contain enough change to provide the refunded amount!";
    public static final String DEFAULT = "Vending Machine could not be initialised!";
}
