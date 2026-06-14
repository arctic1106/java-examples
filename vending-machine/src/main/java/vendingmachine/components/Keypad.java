package vendingmachine.components;

import vendingmachine.product.Product;
import vendingmachine.VendingMachine;

import java.util.Objects;

public class Keypad {
    private final VendingMachine vendingMachine;

    public Keypad(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void enterItemCode(String code) {
        vendingMachine.setSelectedItem(Product.getProductByCode(code));
        vendingMachine.setSelectedItemCode(code);
        String selectedItemString = Objects.requireNonNull(Product.getProductByCode(code)).toString();
        System.out.println("You Selected: " + selectedItemString);
        System.out.println("The currently selected item: " + selectedItemString + "'s price is £"
                + vendingMachine.getSelectedItemPrice());
    }
}
