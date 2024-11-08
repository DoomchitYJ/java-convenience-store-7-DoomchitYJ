package store.service;

import java.util.List;
import store.domain.Inventory;
import store.domain.Product;

public class InventoryService {

    private static Inventory inventory = new Inventory();

    public static boolean isProductExistent(String name) {
        List<Product> products = inventory.findProductByName(name);
        return products.size() > 0;
    }

    public static int getProductQuantity(String name) {
        List<Product> products = inventory.findProductByName(name);
        return products.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public static List<Product> getProducts() {
        return inventory.getProducts();
    }
}
