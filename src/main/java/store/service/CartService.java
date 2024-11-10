package store.service;

import java.util.List;
import store.domain.Cart;

public class CartService {

    private static Cart cart = new Cart();

    public void addToCart(String name, int quantity, int free) {
        cart.addItem(name, quantity, free);
        InventoryService.updateProductQuantity(name, quantity);
    }

    public List<String> getItemsName() {
        return cart.getItemsName();
    }

    public List<Integer> getItemsQuantity() {
        return cart.getItemsQuantity();
    }

    public List<Integer> getItemsFree() {
        return cart.getItemsFree();
    }

    public List<Integer> getItemsPrice() {
        return cart.getItemsPrice();
    }

    public List<Integer> getItemsCost() {
        return cart.getItemsCost();
    }
}
