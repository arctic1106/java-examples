package vendingmachine.product;

import java.util.HashMap;
import java.util.Map;

public class ProductStock {
    private static final Map<Product, Integer> productStock = new HashMap<>();

    static {
        productStock.put(Product.COKE, 10);
    }

    public ProductStock() {
        productStock.put(COKE, 0);
        productStock.put(Product.SPRITE, 0);
        productStock.put(Product.WATER, 0);
        productStock.put(Product.LEMONADE, 0);
        productStock.put(Product.CRISPS, 0);
        productStock.put(Product.PEANUTS, 0);
        productStock.put(Product.CHOCOLATE, 0);
        productStock.put(Product.CANDY, 0);
    }

    public void setStock(Integer stockLevel) {
        for (Map.Entry<Product, Integer> entry : productStock.entrySet()) {
            productStock.put(entry.getKey(), stockLevel);
        }
    }

    public void put(Item keyValue, Integer value) {
        productStock.put((Product) keyValue, value);
    }

    public void insert(Item productStockItem) {
        int stockLevel = productStock.get(productStockItem);
        productStock.put((Product) productStockItem, stockLevel + 1);
    }

    public void reduce(Item reducedItem) {
        int stockLevel = productStock.get(reducedItem);
        productStock.put((Product) reducedItem, stockLevel - 1);
    }

    public Integer get(Item product) {
        return productStock.get(product);
    }

    public Iterable<? extends Map.Entry<Product, Integer>> entrySet() {
        return productStock.entrySet();
    }

    public int size() {
        return productStock.size();
    }
}
