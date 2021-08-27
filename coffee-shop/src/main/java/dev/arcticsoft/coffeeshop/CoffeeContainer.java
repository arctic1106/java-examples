package dev.arcticsoft.coffeeshop;

abstract sealed class CoffeeContainer permits Cup, Bottle {
    private final Coffee coffeeType;

    protected CoffeeContainer(Coffee coffee) {
        coffeeType = coffee;
    }

    protected String getDescription() {
        return coffeeType.getDescription();
    }
}
