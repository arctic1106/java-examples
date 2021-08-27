package dev.arcticsoft.coffeeshop;

final class Bottle<T extends Coffee> extends CoffeeContainer {
    private final int milliLiters;

    public Bottle(T coffee, int milliLiters) {
        super(coffee);
        this.milliLiters = milliLiters;
    }

    protected String getDescription() {
        return "Bottle of " + milliLiters + " milliLiters of " + super.getDescription();
    }
}
