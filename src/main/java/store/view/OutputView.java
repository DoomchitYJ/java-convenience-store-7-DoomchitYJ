package store.view;

import store.domain.Inventory;
import store.domain.Product;

public class OutputView {

    private static final int NO_STOCK = 0;

    private static final String STORE_START_VIEW = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";

    public static void showProducts(Inventory inventory) {
        System.out.println(STORE_START_VIEW);

        for (Product product : inventory.getProducts()) {
            String name = product.getName();
            int price = product.getPrice();
            int quantity = product.getQuantity();
            String promotion = product.getPromotion();

            printProduct(name, price, quantity, promotion);
        }
    }

    private static void printProduct(String name, int price, int quantity, String promotion) {
        if (quantity == NO_STOCK) {
            System.out.printf("- %s %d원 재고 없음 %s\n", name, price, promotion);
            return;
        }

        System.out.printf("- %s %,d원 %,d개 %s\n", name, price, quantity, promotion);
    }
}
