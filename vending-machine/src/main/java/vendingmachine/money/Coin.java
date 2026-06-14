package vendingmachine.money;

import java.math.BigDecimal;

public enum Coin {
    TWO_EUROS(BigDecimal.TWO),
    ONE_EURO(BigDecimal.ONE),
    FIFTY_CENTS(BigDecimal.valueOf(0.5)),
    TWENTY_CENTS(BigDecimal.valueOf(0.2)),
    TEN_CENTS(BigDecimal.valueOf(0.1)),
    FIVE_CENTS(BigDecimal.valueOf(0.05)),
    TWO_CENTS(BigDecimal.valueOf(0.02)),
    ONE_CENT(BigDecimal.valueOf(0.01));

    private final BigDecimal value;

    Coin(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name();
    }

    public BigDecimal value() {
        return value;
    }

    public BigDecimal by(int multiplicand) {
        return value.multiply(BigDecimal.valueOf(multiplicand));
    }
}
