package store.view;

import static store.constant.Constant.NO_FREE;

import java.util.List;
import store.domain.Inventory;
import store.domain.Product;

public class OutputView {

    private static final int NO_STOCK = 0;

    private static final String STORE_START_VIEW = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";

    private static final String STORE_NAME_VIEW = "===========W 편의점=============";
    private static final String PROMOTION_VIEW = "===========증\t정=============";
    private static final String SUMMARY_VIEW = "==============================";

    private static final String HEADER_VIEW = "%-10s %-10s %-10s\n";
    private static final String HEADER_PRODUCT = "상품명";
    private static final String HEADER_QUANTITY = "수량";
    private static final String HEADER_COST = "금액";

    private static final String RECEIPT_CONTENT_FORMAT_1 = "%-10s %-10d %,5d\n";
    private static final String RECEIPT_CONTENT_FORMAT_2 = "%-10s %-10d\n";
    private static final String RECEIPT_CONTENT_FORMAT_3 = "%-10s %-10s %,5d\n";

    private static final String TOTAL_COST = "총구매액";
    private static final String PROMOTION_DISCOUNT = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String FINAL_COST = "내실돈";

    private static final int INDEX_NAME = 0;
    private static final int INDEX_QUANTITY = 1;
    private static final int INDEX_COST = 2;
    private static final int INDEX_FREE = 3;

    private static final int INDEX_TOTAL_QUANTITY = 0;
    private static final int INDEX_TOTAL_COST = 1;
    private static final int INDEX_TOTAL_FREE_PRICE = 2;
    private static final int INDEX_MEMBER_DISCOUNT = 3;
    private static final int INDEX_FINAL_COST = 4;


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
            System.out.printf("- %s %,d원 재고 없음 %s\n", name, price, promotion);
            return;
        }

        System.out.printf("- %s %,d원 %,d개 %s\n", name, price, quantity, promotion);
    }

    public static void showReceipt(List<List<String>> itemsInfo, List<Integer> summaryInfo) {
        List<String> name = itemsInfo.get(INDEX_NAME);
        List<Integer> quantity = itemsInfo.get(INDEX_QUANTITY).stream().map(Integer::parseInt).toList();
        List<Integer> cost = itemsInfo.get(INDEX_COST).stream().map(Integer::parseInt).toList();
        List<Integer> free = itemsInfo.get(INDEX_FREE).stream().map(Integer::parseInt).toList();

        System.out.println(STORE_NAME_VIEW);
        System.out.printf(HEADER_VIEW, HEADER_PRODUCT, HEADER_QUANTITY, HEADER_COST);
        for (int i = 0; i < name.size(); i++) {
            System.out.printf(RECEIPT_CONTENT_FORMAT_1, name.get(i), quantity.get(i), cost.get(i));
        }
        System.out.println(PROMOTION_VIEW);
        for (int i = 0; i < name.size(); i++) {
            if (free.get(i) != NO_FREE) {
                System.out.printf(RECEIPT_CONTENT_FORMAT_2, name.get(i), free.get(i));
            }
        }
        System.out.println(SUMMARY_VIEW);
        System.out.printf(RECEIPT_CONTENT_FORMAT_1,
                TOTAL_COST, summaryInfo.get(INDEX_TOTAL_QUANTITY), summaryInfo.get(INDEX_TOTAL_COST));
        System.out.printf(RECEIPT_CONTENT_FORMAT_3, PROMOTION_DISCOUNT, " ", -summaryInfo.get(INDEX_TOTAL_FREE_PRICE));
        System.out.printf(RECEIPT_CONTENT_FORMAT_3, MEMBERSHIP_DISCOUNT, " ", -summaryInfo.get(INDEX_MEMBER_DISCOUNT));
        System.out.printf(RECEIPT_CONTENT_FORMAT_3, FINAL_COST, " ", summaryInfo.get(INDEX_FINAL_COST));
    }
}
