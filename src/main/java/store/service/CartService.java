package store.service;

import java.util.List;
import store.domain.Cart;

public class CartService {

    private static InventoryService inventoryService;

    private static Cart cart = new Cart();

    public CartService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addToCart(String name, int quantity, int free) {
        cart.addItem(inventoryService.getProduct(name), quantity, free);
        inventoryService.updateProductQuantity(name, quantity);
    }

    public void clearCart() {
        this.cart = new Cart();
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
