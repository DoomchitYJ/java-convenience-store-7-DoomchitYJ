package store.domain;

public class CartItem {

    private Product product;
    private int quantity;
    private int free;

    public CartItem(final Product product, final int quantity, final int free) {
        this.product = product;
        this.quantity = quantity;
        this.free = free;
    }

}
