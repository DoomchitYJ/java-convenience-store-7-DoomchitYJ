package store.domain;

import static store.constant.Constant.MAX_TRY;
import static store.constant.Constant.REPLY_NO;
import static store.constant.Constant.REPLY_YES;
import static store.view.ErrorPrinter.printError;

import java.util.Date;
import store.service.CartService;
import store.service.InventoryService;
import store.view.InputView;

public class PurchasePolicy {

    private static final Promotions promotions = new Promotions();

    public static void buy(Order order) {
        Promotion promotion = promotions.findPromotionByName(
                InventoryService.getPromotionNameByProductName(order.getName()));
        applyPromotion(order, promotion);
    }

    private static void applyPromotion(Order order, Promotion promotion) {
        CartService cartService = new CartService();
        if (!isPromotionValid(promotion)) {
            applyNoPromotion(cartService, order, promotion);
            return;
        }
        if (isBonusPossible(order, promotion)) {
            handleBonusPossible(cartService, order, promotion);
            return;
        }
        if (isPromotionStockOut(order)) {
            handleStockOut(cartService, order, promotion);
            return;
        }
        cartService.addToCart(order.getName(), order.getQuantity(), calculateFree(order, promotion));
    }

    private static void applyNoPromotion(CartService cartService, Order order, Promotion promotion) {
        cartService.addToCart(order.getName(), order.getQuantity(), calculateFree(order, promotion));
    }

    private static void handleBonusPossible(CartService cartService, Order order, Promotion promotion) {
        for (int i = 1; i < MAX_TRY; i++) {
            try {
                int free = calculateFree(order, promotion);
                String reply = InputView.readMoreFree(order.getName());
                if (reply.equals(REPLY_YES)) {
                    free++;
                }
                cartService.addToCart(order.getName(), order.getQuantity(), free);
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
    }

    private static void handleStockOut(CartService cartService, Order order, Promotion promotion) {
        for (int i = 1; i < MAX_TRY; i++) {
            try {
                int free = calculateFree(order, promotion);
                int quantity = order.getQuantity();
                int promotionQuantity = InventoryService.getPromotionProductQuantity(order.getName());
                int left = promotionQuantity - free * (promotion.getBuy() + promotion.getGet());
                String reply = InputView.readNoDiscount(order.getName(), left);
                if (reply.equals(REPLY_NO)) {
                    quantity -= left;
                }
                cartService.addToCart(order.getName(), quantity, free);
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }

        }
    }

    private static int calculateFree (Order order, Promotion promotion){
        int promotionQuantity = InventoryService.getPromotionProductQuantity(order.getName());
        if (promotionQuantity >= order.getQuantity()) {
            return (order.getQuantity() / (promotion.getBuy() + promotion.getGet()));
        }
        return (promotionQuantity / (promotion.getBuy() + promotion.getGet()));
    }

    private static boolean isPromotionValid (Promotion promotion){
        if (promotion == null) {
            return false;
        }
        Date today = new Date();
        return today.after(promotion.getStartDate()) && today.before(promotion.getEndDate());
    }

    private static boolean isBonusPossible (Order order, Promotion promotion){
        return order.getQuantity() % (promotion.getBuy() + promotion.getGet()) == promotion.getBuy()
                && InventoryService.getPromotionProductQuantity(order.getName())
                > order.getQuantity() + promotion.getGet();
    }

    private static boolean isPromotionStockOut (Order order){
        return order.getQuantity() > InventoryService.getPromotionProductQuantity(order.getName());
    }
}