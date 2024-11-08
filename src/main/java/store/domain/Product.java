package store.domain;

public class Product {

    private static String name;

    private static int price;

    private static int quantity;

    private static String description;

    public Product(final String name, final int price, final int quantity, final String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public void updateQuantity(final int amount) {
        quantity += amount;
    }
}
