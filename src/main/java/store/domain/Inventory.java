package store.domain;

import java.util.List;
import store.service.InventoryService;

public class Inventory {

    private static List<Product> products;

    public Inventory() {
        this.products = InventoryService.setUpInventory();
    }

    public List<Product> getProducts() {
        return products;
    }
}
