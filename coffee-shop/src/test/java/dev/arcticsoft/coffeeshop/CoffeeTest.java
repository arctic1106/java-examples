package dev.arcticsoft.coffeeshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoffeeTest {

    @Test
    void constructCoffee() {
        assertEquals(Coffee1.class, Coffee1.make().getClass());
        assertEquals(Coffee2.class, Coffee2.make().getClass());
    }

    @Test
    void getDescription() {
        assertEquals("Coffee1, which is GROUND_BEAN LIGHT", Coffee1.make().getDescription());
        assertEquals("Coffee2, which is WHOLE_BEAN DARK", Coffee2.make().getDescription());
    }

    @Test
    void makeCoffe() {
        assertEquals("Coffee1, which is GROUND_BEAN LIGHT", Coffee.make("Coffee1").getDescription());
    }

    @Test
    void wrongCoffeeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Coffee.make("CoffeeSpecial"));
    }
}
