package store.repository;

import static store.constant.Constant.DELIMITER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsRepository {

    private static final int PRODUCTS_START_INDEX = 1;

    private final Path filePath;

    public ProductsRepository(final String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<List<String>> readProductsFile() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        String[] header = lines.getFirst().split(DELIMITER);

        List<List<String>> products = addProducts(lines, header);

        return products;
    }

    private List<List<String>> addProducts(List<String> lines, String[] header) {
        List<List<String>> products = new ArrayList<>();

        for (int i = PRODUCTS_START_INDEX; i < lines.size(); i++) {
            String[] line = lines.get(i).split(DELIMITER);
            List<String> productInfo = new ArrayList<>();

            for (int j = 0; j < header.length; j++) {
                productInfo.add(line[j].trim());
            }
            products.add(productInfo);
        }

        return products;
    }
}
