package store.domain;

import static store.config.Congfig.PRODUCTS_FILE_PATH;

import java.util.List;
import java.util.stream.Collectors;
import store.repository.ProductsRepository;

public class Inventory {

    private static final ProductsRepository productsRepository = new ProductsRepository(PRODUCTS_FILE_PATH);

    private static List<Product> products;

    public Inventory() {
        this.products = productsRepository.loadInventory();
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> findProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());
    }
}
