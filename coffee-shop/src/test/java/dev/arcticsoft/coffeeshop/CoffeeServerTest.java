package dev.arcticsoft.coffeeshop;

import org.junit.jupiter.api.Test;

import dev.arcticsoft.coffeeshop.Cup.CupSize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoffeeServerTest {

    @Test
    public void serveCoffeeTest() {
        assertEquals(
                "BIG cup of Coffee1, which is GROUND_BEAN LIGHT",
                CoffeeServer.serveCoffee(Coffee.make("Coffee1"), "Cup", CupSize.BIG).getDescription());

        assertEquals(
                Bottle.class,
                CoffeeServer.serveCoffee(Coffee.make("Coffee1"), "Bottle", 500).getClass());

        assertEquals(
                Cup.class,
                CoffeeServer.serveCoffee(Coffee.make("Coffee2"), "Cup", CupSize.MEDIUM).getClass());

        assertThrows(
                IllegalArgumentException.class,
                () -> CoffeeServer.serveCoffee(Coffee.make("Coffee1"), "Cup", 233));

        assertThrows(
                IllegalArgumentException.class,
                () -> CoffeeServer.serveCoffee(Coffee.make("Coffee1"), "Bottle", CupSize.MEDIUM));
    }
}
