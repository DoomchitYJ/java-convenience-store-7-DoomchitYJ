package store.domain;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
