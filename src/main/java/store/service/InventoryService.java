package store.service;

import static store.config.Congfig.PRODUCTS_FILE_PATH;
import static store.constant.Constant.INDEX_NAME;
import static store.constant.Constant.INDEX_PRICE;
import static store.constant.Constant.INDEX_PROMOTION;
import static store.constant.Constant.INDEX_QUANTITY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.repository.ProductsRepository;

public class InventoryService {

    public static List<Product> setUpInventory() {
        List<Product> inventory = new ArrayList<Product>();

        ProductsRepository productsRepository = new ProductsRepository(PRODUCTS_FILE_PATH);
        try {
            List<List<String>> products = productsRepository.readProductsFile();
            for (List<String> product : products) {
                Product item = new Product(product.get(INDEX_NAME), Integer.parseInt(product.get(INDEX_PRICE)),
                        Integer.parseInt(product.get(INDEX_QUANTITY)), product.get(INDEX_PROMOTION));
                inventory.add(item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inventory;
    }
}
