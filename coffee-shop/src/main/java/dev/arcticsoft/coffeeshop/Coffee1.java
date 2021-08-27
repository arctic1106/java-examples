package dev.arcticsoft.coffeeshop;

final class Coffee1 extends Coffee {

    private Coffee1() {
        super(BeanType.GROUND_BEAN, Roast.LIGHT);
    }

    public static Coffee make() {
        return new Coffee1();
    }
}
