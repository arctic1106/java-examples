package dev.arcticsoft.coffeeshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoffeeContainerTest {

    @Test
    void getDescription() {
        var bottle = new Bottle<>(Coffee1.make(), 333);
        assertEquals("Bottle of 333 milliLiters of Coffee1, which is GROUND_BEAN LIGHT", bottle.getDescription());

        var cup = new Cup<>(Coffee2.make(), Cup.CupSize.MEDIUM);
        assertEquals("MEDIUM cup of Coffee2, which is WHOLE_BEAN DARK", cup.getDescription());
    }
}
