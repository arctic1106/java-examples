package vendingmachine.money;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoinStock {
    private static final Map<Coin, Integer> coinStock = new HashMap<>();

    static {
        Arrays.stream(Coin.values()).forEach(coin -> coinStock.put(coin, 0));
    }

    public void put(Coin keyValue, Integer value) {
        coinStock.put((Coin) keyValue, value);
    }

    public BigDecimal getMachineTotalMoneyValue() {
        var totalMoney = BigDecimal.ZERO;
        for (Map.Entry<Coin, Integer> entry : coinStock.entrySet()) {
            totalMoney = totalMoney.add(entry.getKey().by(entry.getValue()));
        }
        return totalMoney;
    }

    public void insert(Coin insertedItem) {
        coinStock.compute(insertedItem, (k, stockLevel) -> stockLevel + 1);
    }

    public void reduce(Coin reducedItem) {
        coinStock.compute(reducedItem, (k, stockLevel) -> stockLevel - 1);
    }

    public Integer get(Coin coin) {
        return coinStock.get(coin);
    }

    public Iterable<? extends Map.Entry<Coin, Integer>> entrySet() {
        return coinStock.entrySet();
    }

    public int size() {
        return coinStock.size();
    }
}
