package dev.arcticsoft.coffeeshop;

final class Coffee2 extends Coffee {

    private Coffee2() {
        super(BeanType.WHOLE_BEAN, Roast.DARK);
    }

    public static Coffee make() {
        return new Coffee2();
    }
}
