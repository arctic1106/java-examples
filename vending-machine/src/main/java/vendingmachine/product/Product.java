package vendingmachine.product;

import java.math.BigDecimal;

public enum Product {
    COKE("0001", BigDecimal.valueOf(2.00)),
    SPRITE("0002", BigDecimal.valueOf(2.00)),
    WATER("0003", BigDecimal.valueOf(1.50)),
    LEMONADE("0004", BigDecimal.valueOf(1.75)),
    CRISPS("1001", BigDecimal.valueOf(1.75)),
    PEANUTS("1002", BigDecimal.valueOf(1.75)),
    CHOCOLATE("1003", BigDecimal.valueOf(1.75)),
    CANDY("1004", BigDecimal.valueOf(1.75));

    private final String code;
    private final BigDecimal price;

    Product(String code, BigDecimal price) {
        this.code = code;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Product getProductByCode(String code) {
        for (Product product : values()) {
            if (product.getCode().equals(code)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Invalid product code: " + code);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.name();
    }
}