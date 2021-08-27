package dev.arcticsoft.coffeeshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoffeeShopTest {

    @Test
    void listShoppingCart() {
        var coffeeShop = new CoffeeShop();
        coffeeShop.buyCoffee("Coffee1", "Cup", Cup.CupSize.SMALL);
        coffeeShop.buyCoffee("Coffee2", "Bottle", 333);

        var expected = """
                You have the following items in your shopping cart:
                - SMALL cup of Coffee1, which is GROUND_BEAN LIGHT
                - Bottle of 333 milliLiters of Coffee2, which is WHOLE_BEAN DARK
                """;
        assertEquals(expected, coffeeShop.listOrders());
    }
}
