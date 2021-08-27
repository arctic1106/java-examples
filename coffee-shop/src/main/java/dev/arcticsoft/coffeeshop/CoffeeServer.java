package dev.arcticsoft.coffeeshop;

import dev.arcticsoft.coffeeshop.Cup.CupSize;

public class CoffeeServer {

    static CoffeeContainer serveCoffee(Coffee coffee, String container, CupSize cupSize) {
        if (!container.equals("Cup")) {
            throw new IllegalArgumentException("Cup size needs a Cup container");
        }
        return serveCoffee(coffee, null, cupSize, null);
    }

    static CoffeeContainer serveCoffee(Coffee coffee, String container, int milliLiters) {
        if (!container.equals("Bottle")) {
            throw new IllegalArgumentException("milliliters needs a Bottle container");
        }
        return serveCoffee(coffee, null, null, milliLiters);
    }

    private static CoffeeContainer serveCoffee(Coffee coffee, String container, CupSize cupSize, Integer milliLiters) {
        if (cupSize == null) {
            return new Bottle<>(coffee, milliLiters);
        } else {
            return new Cup<>(coffee, cupSize);
        }
    }
}
