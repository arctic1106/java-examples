package dev.arcticsoft.coffeeshop;

abstract sealed class Coffee permits Coffee1, Coffee2 {
    private final BeanType beanType;
    private final Roast roast;

    protected Coffee(BeanType beanType, Roast roast) {
        this.beanType = beanType;
        this.roast = roast;
    }

    protected static Coffee make(String coffeeName) {
        return switch (coffeeName) {
            case "Coffee1" -> Coffee1.make();
            case "Coffee2" -> Coffee2.make();
            default -> throw new IllegalArgumentException("Coffee types are Coffee1 & Coffee2");
        };
    }

    protected String getDescription() {
        return this.getClass().getSimpleName() + ", which is " + beanType + " " + roast;
    }

    enum BeanType {
        GROUND_BEAN, WHOLE_BEAN
    }

    enum Roast {
        LIGHT, MEDIUM, DARK
    }
}
