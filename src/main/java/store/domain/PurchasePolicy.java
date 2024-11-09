package store.domain;

import static store.constant.Constant.REPLY_NO;
import static store.constant.Constant.REPLY_YES;

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
        String name = order.getName();
        int quantity = order.getQuantity();
        int free = calculateFree(order, promotion);

        if (!isPromotionValid(promotion)) {
            cartService.addToCart(name, quantity, free);
            return;
        }

        if (isBonusPossible(order, promotion)) {
            String reply = InputView.readMoreFree(order.getName());
            if (reply.equals(REPLY_YES)) {
                free++;
            }
            cartService.addToCart(name, quantity, free);
            return;
        }

        if (isPromotionStockOut(order)) {
            int promotionQuantity = InventoryService.getPromotionProductQuantity(order.getName());
            int left = promotionQuantity - free * (promotion.getBuy()+promotion.getGet());
            String reply = InputView.readNoDiscount(order.getName(), left);
            if (reply.equals(REPLY_NO)) {
                quantity -= left;
            }
            cartService.addToCart(name, quantity, free);
        }
    }

    private static int calculateFree(Order order, Promotion promotion) {
        int promotionQuantity = InventoryService.getPromotionProductQuantity(order.getName());
        if (promotionQuantity >= order.getQuantity()) {
            return (int) (order.getQuantity() / (promotion.getBuy() + promotion.getGet()));
        }
        return (int) (promotionQuantity / (promotion.getBuy() + promotion.getGet()));
    }

    private static boolean isPromotionValid(Promotion promotion) {
        if (promotion == null) {
            return false;
        }
        Date today = new Date();
        return today.after(promotion.getStartDate()) && today.before(promotion.getEndDate());
    }

    private static boolean isBonusPossible(Order order, Promotion promotion) {
        return order.getQuantity() % (promotion.getBuy() + promotion.getGet()) == promotion.getBuy()
                && InventoryService.getPromotionProductQuantity(order.getName())
                > order.getQuantity() + promotion.getGet();
    }

    private static boolean isPromotionStockOut(Order order) {
        return order.getQuantity() > InventoryService.getPromotionProductQuantity(order.getName());
    }
}
