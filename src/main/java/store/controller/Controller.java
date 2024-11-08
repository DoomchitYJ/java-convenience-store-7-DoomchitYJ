package store.controller;

import store.domain.Inventory;
import store.view.OutputView;

public class Controller {

    public void run() {

        Inventory inventory = new Inventory();
        OutputView.showProducts(inventory);
    }
}
