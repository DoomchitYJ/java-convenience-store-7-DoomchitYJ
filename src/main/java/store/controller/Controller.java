package store.controller;

import static store.constant.Constant.MAX_TRY;
import static store.exception.ExceptionMessage.MAX_TRY_ERROR;
import static store.exception.ExceptionMessage.NON_EXISTENT_PRODUCT;
import static store.view.ErrorPrinter.printError;

import java.util.List;
import store.domain.Inventory;
import store.domain.Order;
import store.exception.StoreException;
import store.view.InputView;
import store.view.OutputView;

public class Controller {

    private Inventory inventory = new Inventory();

    public void run() {

        OutputView.showProducts(inventory);
        List<Order> orders = InputView.readOrder();

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
        }
    }

    private boolean hasProduct(Order order) {
        return inventory.isProductExistent(order.getName());
    }
}
