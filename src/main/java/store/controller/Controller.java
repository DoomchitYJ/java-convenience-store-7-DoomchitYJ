package store.controller;

import static store.constant.Constant.MAX_TRY;
import static store.exception.ExceptionMessage.MAX_TRY_ERROR;
import static store.exception.ExceptionMessage.NON_EXISTENT_PRODUCT;
import static store.exception.ExceptionMessage.TOO_MANY_ORDER;
import static store.view.ErrorPrinter.printError;

import java.util.List;
import store.domain.Order;
import store.domain.PurchasePolicy;
import store.exception.StoreException;
import store.service.CartService;
import store.service.InventoryService;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

public class Controller {

    public void run() {

        InventoryService inventoryService = new InventoryService();
        boolean running = true;
        while (running) {
            OutputView.showProducts(inventoryService.getInventory());
            List<Order> orders = readOrder(inventoryService);

            CartService cartService = new CartService(inventoryService);
            PurchasePolicy purchasePolicy = new PurchasePolicy(inventoryService, cartService);
            for (Order order : orders) {
                purchasePolicy.buy(order);
            }
            PurchaseService purchaseService = new PurchaseService(cartService);
            purchaseService.completePurchase();

            if (dontWantMoreOrder()) running = false;
        }

    }

    private List<Order> readOrder(InventoryService inventoryService) {
        for (int i=1; i<=MAX_TRY; i++) {
            try {
                List<Order> orders = InputView.readOrder();
                validateOrders(inventoryService, orders);

                return orders;
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
        throw new StoreException(MAX_TRY_ERROR);
    }

    private void validateOrders(InventoryService inventoryService, List<Order> orders) {
        for (Order order : orders) {
            if (!hasProduct(inventoryService, order)) {
                throw new StoreException(NON_EXISTENT_PRODUCT);
            }
            if (!isStockAvailable(inventoryService, order)) {
                throw new StoreException(TOO_MANY_ORDER);
            }
        }
    }

    private boolean hasProduct(InventoryService inventoryService, Order order) {
        return inventoryService.isProductExistent(order.getName());
    }

    private boolean isStockAvailable(InventoryService inventoryService, Order order) {
        return inventoryService.getProductQuantity(order.getName()) >= order.getQuantity();
    }

    private boolean dontWantMoreOrder() {
        for (int i=1; i<=MAX_TRY; i++) {
            try {
                return InputView.readMoreOrder().equals("N");
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
        throw new StoreException(MAX_TRY_ERROR);
    }
}
