package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.service.InventoryService;

public class Cart {

    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<CartItem>();
    }

    public void addItem(String name, int quantity, int free) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setFree(item.getFree() + free);
                return;
            }
        }
        Product product = InventoryService.getProduct(name);
        items.add(new CartItem(product, quantity, free));
    }
}
