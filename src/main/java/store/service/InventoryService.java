package store.service;

import static store.config.Congfig.PRODUCTS_FILE_PATH;
import static store.constant.Constant.INDEX_NAME;
import static store.constant.Constant.INDEX_PRICE;
import static store.constant.Constant.INDEX_PROMOTION;
import static store.constant.Constant.INDEX_QUANTITY;
import static store.constant.Constant.NULL;

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
                Product item = convertIntoProduct(product);
                inventory.add(item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inventory;
    }

    private static Product convertIntoProduct(List<String> product) {
        String name = product.get(INDEX_NAME);
        int price = Integer.parseInt(product.get(INDEX_PRICE));
        int quantity = Integer.parseInt(product.get(INDEX_QUANTITY));
        String promotion = getPromotion(product);

        return new Product(name, price, quantity, promotion);
    }

    private static String getPromotion(List<String> product) {
        String promotion = product.get(INDEX_PROMOTION);
        if (promotion.equals(NULL)) { return null; }

        return promotion;
    }
}
