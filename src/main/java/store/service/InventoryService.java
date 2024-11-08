package store.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Inventory;
import store.domain.Product;

public class InventoryService {

    private static Inventory inventory = new Inventory();

    public static boolean isProductExistent(String name) {
        List<Product> products = inventory.findProductByName(name);
        return products.size() > 0;
    }

    public static int getProductQuantity(String name) {
        List<Product> products = inventory.findProductByName(name);
        return products.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public static int getPromotionProductQuantity(String name) {
        List<Product> products = inventory.findProductByName(name);
        return products.stream()
                .filter(p -> !p.getPromotion().isEmpty())
                .findFirst()
                .map(Product::getQuantity)
                .orElse(0);
    }

    public static String getPromotionNameByProductName(String name) {
        List<Product> products = inventory.findProductByName(name);
        return products.stream()
                .filter(p -> !p.getPromotion().isEmpty())
                .map(Product::getPromotion)
                .findFirst()
                .orElse("");
    }

    public static Product getProduct(String name) {
        return inventory.findProductByName(name).stream()
                .findFirst()
                .orElse(null);
    }

    public static List<Product> getProducts() {
        return inventory.getProducts();
    }

    public static void updateProductQuantity(String name, int quantity) {
        List<Product> products = inventory.findProductByName(name).stream()
                        .sorted(Comparator.comparing((Product p) -> !p.getPromotion().isEmpty()))
                        .collect(Collectors.toList());
        update(products, quantity);
    }

    private static void update(List<Product> products, int quantity) {
        for (Product product : products) {
            if (product.getQuantity() >= quantity) {
                product.updateQuantity(-quantity);
                break;
            }
            quantity -= product.getQuantity();
            product.updateQuantity(-product.getQuantity());
        }
    }
}
