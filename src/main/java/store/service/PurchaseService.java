package store.service;

import java.util.List;

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
}