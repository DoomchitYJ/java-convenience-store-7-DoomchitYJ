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
import store.service.InventoryService;
import store.view.InputView;
import store.view.OutputView;

public class Controller {

    public void run() {

        OutputView.showProducts();
        List<Order> orders = InputView.readOrder();

        for (Order order : orders) {
            PurchasePolicy.buy(order);
        }
    }

    private List<Order> readOrder() {
        for (int i=1; i<=MAX_TRY; i++) {
            try {
                List<Order> orders = InputView.readOrder();
                validateOrders(orders);

                return orders;
            } catch (IllegalArgumentException e) {
                printError(e.getMessage());
            }
        }
        throw new StoreException(MAX_TRY_ERROR);
    }

    private void validateOrders(List<Order> orders) {
        for (Order order : orders) {
            if (!hasProduct(order)) {
                throw new StoreException(NON_EXISTENT_PRODUCT);
            }
            if (!isStockAvailable(order)) {
                throw new StoreException(TOO_MANY_ORDER);
            }
        }
    }

    private boolean hasProduct(Order order) {
        return InventoryService.isProductExistent(order.getName());
    }

    private boolean isStockAvailable(Order order) {
        return InventoryService.getProductQuantity(order.getName()) >= order.getQuantity();
    }
}
