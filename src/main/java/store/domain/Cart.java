package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<CartItem>();
    }

    public void addItem(Product product, int quantity, int free) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setFree(item.getFree() + free);
                return;
            }
        }
        items.add(new CartItem(product, quantity, free));
    }
}
