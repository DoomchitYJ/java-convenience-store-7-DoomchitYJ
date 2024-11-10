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

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFree() {
        return free;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public void setFree(final int free) {
        this.free = free;
    }

    public int getPrice() {
        return product.getPrice();
    }

    public int getCost() {
        return product.getPrice() * quantity;
    }
}
