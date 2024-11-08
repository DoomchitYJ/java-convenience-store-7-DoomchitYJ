package store.service;

import store.domain.Cart;

public class CartService {

    private Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addToCart(String name, int quantity, int free) {
        cart.addItem(name, quantity, free);
        InventoryService.updateProductQuantity(name, quantity);
    }
}
