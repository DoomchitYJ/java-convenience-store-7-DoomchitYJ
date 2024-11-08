package store.view;

import static store.constant.Constant.INDEX_NAME;
import static store.constant.Constant.INDEX_PRICE;
import static store.constant.Constant.INDEX_PROMOTION;
import static store.constant.Constant.INDEX_QUANTITY;

import java.util.List;

public class OuputView {

    private static final String NULL = "null";

    private static final int NO_STOCK = 0;

    private static final String STORE_START_VIEW = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";

    public static void showProducts(List<List<String>> products) {
        System.out.println(STORE_START_VIEW);

        for (List<String> product : products) {
            String name = product.get(INDEX_NAME);
            int price = Integer.parseInt(product.get(INDEX_PRICE));
            int quantity = Integer.parseInt(product.get(INDEX_QUANTITY));
            String promotion = getPromotion(product);

            printProduct(name, price, quantity, promotion);
        }
    }

    private static String getPromotion(List<String> product) {
        String promotion = product.get(INDEX_PROMOTION);
        if (promotion.equals(NULL)) { return null; }

        return promotion;
    }

    private static void printProduct(String name, int price, int quantity, String promotion) {
        if (quantity == NO_STOCK) {
            System.out.printf("- %s %d원 재고 없음 %s\n", name, price, promotion);
            return;
        }

        System.out.printf("- %s %,d원 %,d개 %s\n", name, price, quantity, promotion);
    }
}
