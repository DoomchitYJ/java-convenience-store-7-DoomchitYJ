package store.domain;

import java.util.List;
import java.util.stream.Collectors;
import store.service.InventoryService;

public class Inventory {

    private static List<Product> products;

    public Inventory() {
        this.products = InventoryService.setUpInventory();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isProductExistent(String name) {
        return products.stream().anyMatch(p -> p.getName().equals(name));
    }

    public List<Product> findProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());
    }
}
