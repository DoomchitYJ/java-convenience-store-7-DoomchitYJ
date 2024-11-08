package store.domain;

public class Product {

    private static String name;

    private static int price;

    private static int quantity;

    private static String promotion;

    public Product(final String name, final int price, final int quantity, final String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void updateQuantity(final int amount) {
        quantity += amount;
    }

    public static int getName() {
        return quantity;
    }

    public static int getPrice() {
        return price;
    }

    public static String getPromotion() {
        return promotion;
    }
}
