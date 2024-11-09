package store.repository;

import static store.constant.Constant.CONTENTS_START_INDEX;
import static store.constant.Constant.DELIMITER;
import static store.constant.Constant.INDEX_NAME;
import static store.constant.Constant.INDEX_PRICE;
import static store.constant.Constant.INDEX_PROMOTION;
import static store.constant.Constant.INDEX_QUANTITY;
import static store.constant.Constant.NULL;
import static store.exception.ExceptionMessage.FILE_PATH_ERROR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.exception.StoreException;

public class ProductsRepository {


    private final Path filePath;

    public ProductsRepository(final String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Product> loadInventory() {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Product> inventory = new ArrayList<>();
            for (int i = CONTENTS_START_INDEX; i < lines.size(); i++) {
                String[] product = lines.get(i).split(DELIMITER);
                inventory.add(convertIntoProduct(product));
            }
            return inventory;
        } catch (IOException e) {
            throw new StoreException(FILE_PATH_ERROR);
        }
    }

    private static Product convertIntoProduct(String[] product) {
        String name = product[INDEX_NAME];
        int price = Integer.parseInt(product[INDEX_PRICE]);
        int quantity = Integer.parseInt(product[INDEX_QUANTITY]);
        String promotion = getPromotion(product);

        return new Product(name, price, quantity, promotion);
    }

    private static String getPromotion(String[] product) {
        String promotion = product[INDEX_PROMOTION];
        if (promotion.equals(NULL)) { return ""; }

        return promotion;
    }
}
