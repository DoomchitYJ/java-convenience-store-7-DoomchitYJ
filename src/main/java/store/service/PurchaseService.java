package store.service;

import static store.constant.Constant.MAX_TRY;
import static store.constant.Constant.NO_FREE;
import static store.exception.ExceptionMessage.MAX_TRY_ERROR;
import static store.view.ErrorPrinter.printError;

import java.util.List;
import java.util.stream.IntStream;
import store.exception.StoreException;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseService {

    private final CartService cartService;

    public PurchaseService(final CartService cartService) {
        this.cartService = cartService;
    }

    public void completePurchase() {

        makeReceipt();
    }

    private void makeReceipt() {
        List<List<String>> itemsInfo = prepareItemsInfo();
        List<Integer> summaryInfo = prepareSummaryInfo();
        OutputView.showReceipt(itemsInfo, summaryInfo);
    }

    private List<List<String>> prepareItemsInfo() {
        List<String> name = cartService.getItemsName();
        List<String> quantity = cartService.getItemsQuantity().stream()
                .map(String::valueOf)
                .toList();
        List<String> cost = cartService.getItemsCost().stream()
                .map(String::valueOf)
                .toList();
        List<String> free = cartService.getItemsFree().stream()
                .map(String::valueOf)
                .toList();

        return List.of(name, quantity, cost, free);
    }

    private List<Integer> prepareSummaryInfo() {
        int totalQuantity = cartService.getItemsQuantity().stream()
                .mapToInt(x -> x)
                .sum();
        int totalCost = calculateTotalCost();
        int totalFreePrice = calculateTotalFreePrice();
        int memberDiscount = calculateMemberDiscount();
        int finalCost = totalCost - totalFreePrice - memberDiscount;

        return List.of(totalQuantity, totalCost, totalFreePrice, memberDiscount, finalCost);
    }

    private int calculateTotalCost() {
        return cartService.getItemsCost().stream()
                .mapToInt(x -> x)
                .sum();
    }

    private int calculateTotalFreePrice() {
        return IntStream.range(0, cartService.getItemsFree().size())
                .map(i -> cartService.getItemsFree().get(i) * cartService.getItemsPrice().get(i))
                .sum();
    }

    private int calculateMemberDiscount() {
        List<Integer> free = cartService.getItemsFree();
        List<Integer> cost = cartService.getItemsCost();
        int noPromotionCost = IntStream.range(0, free.size())
                .filter(i -> free.get(i) == NO_FREE)
                .map(cost::get)
                .sum();
        if (isMembershipDiscount()) {
            int memberDiscount = (int) (noPromotionCost * 0.3);
            if (memberDiscount > 8000) memberDiscount = 8000;
            return memberDiscount;
        }
        return 0;
    }

    private boolean isMembershipDiscount() {
        for (int i = 1; i <= MAX_TRY; i++) {
            try {
                String reply = InputView.readMembership();
                if (reply.equals("Y")) return true;
                return false;
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
        throw new StoreException(MAX_TRY_ERROR);
    }
}
