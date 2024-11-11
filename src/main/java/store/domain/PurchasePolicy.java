package store.domain;

import static store.constant.Constant.MAX_TRY;
import static store.constant.Constant.NO_FREE;
import static store.constant.Constant.REPLY_NO;
import static store.constant.Constant.REPLY_YES;
import static store.exception.ExceptionMessage.MAX_TRY_ERROR;
import static store.view.ErrorPrinter.printError;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import store.exception.StoreException;
import store.service.CartService;
import store.service.InventoryService;
import store.view.InputView;

public class PurchasePolicy {

    private static final Promotions promotions = new Promotions();

    private static InventoryService inventoryService;

    private static CartService cartService;

    public PurchasePolicy(InventoryService inventoryService, CartService cartService) {
        this.inventoryService = inventoryService;
        this.cartService = cartService;
    }
    public void buy(Order order) {

        Promotion promotion = promotions.findPromotionByName(
                inventoryService.getPromotionNameByProductName(order.getName()));
        applyPromotion(order, promotion);
    }

    private static void applyPromotion(Order order, Promotion promotion) {
        if (!isPromotionValid(promotion)) {
            applyNoPromotion(order);
            return;
        }
        if (isBonusPossible(order, promotion)) {
            handleBonusPossible(order, promotion);
            return;
        }
        if (isPromotionStockOut(order)) {
            handleStockOut(order, promotion);
            return;
        }
        cartService.addToCart(order.getName(), order.getQuantity(), calculateFree(order, promotion));
    }

    private static void applyNoPromotion(Order order) {
        cartService.addToCart(order.getName(), order.getQuantity(), NO_FREE);
    }

    private static void handleBonusPossible(Order order, Promotion promotion) {
        for (int i = 1; i < MAX_TRY; i++) {
            try {
                int free = calculateFree(order, promotion);
                int quantity = order.getQuantity();
                String reply = InputView.readMoreFree(order.getName());
                if (reply.equals(REPLY_YES)) {
                    free++;
                    quantity++;
                }
                cartService.addToCart(order.getName(), quantity, free);
                return;
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
        throw new StoreException(MAX_TRY_ERROR);
    }

    private static void handleStockOut(Order order, Promotion promotion) {
        for (int i = 1; i < MAX_TRY; i++) {
            try {
                int free = calculateFree(order, promotion);
                int quantity = order.getQuantity();
                int promotionQuantity = inventoryService.getPromotionProductQuantity(order.getName());
                int left = promotionQuantity - free * (promotion.getBuy() + promotion.getGet());
                String reply = InputView.readNoDiscount(order.getName(), left);
                if (reply.equals(REPLY_NO)) {
                    quantity -= left;
                }
                cartService.addToCart(order.getName(), quantity, free);
                return;
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }

        }
    }

    private static int calculateFree (Order order, Promotion promotion){
        int promotionQuantity = inventoryService.getPromotionProductQuantity(order.getName());
        if (promotionQuantity >= order.getQuantity()) {
            return (order.getQuantity() / (promotion.getBuy() + promotion.getGet()));
        }
        return (promotionQuantity / (promotion.getBuy() + promotion.getGet()));
    }

    private static boolean isPromotionValid (Promotion promotion){
        if (promotion == null) {
            return false;
        }
        LocalDateTime today = DateTimes.now();
        return today.isAfter(promotion.getStartDate()) && today.isBefore(promotion.getEndDate());
    }

    private static boolean isBonusPossible (Order order, Promotion promotion){
        return order.getQuantity() % (promotion.getBuy() + promotion.getGet()) == promotion.getBuy()
                && inventoryService.getPromotionProductQuantity(order.getName())
                > order.getQuantity() + promotion.getGet();
    }

    private static boolean isPromotionStockOut (Order order){
        return order.getQuantity() > inventoryService.getPromotionProductQuantity(order.getName());
    }
}