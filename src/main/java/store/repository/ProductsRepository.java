package store.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProductsRepository {

    private final Path filePath;

    public ProductsRepository(final Path filePath) {
        this.filePath = filePath;
    }

    public List<String> readProductsFile() throws IOException {
        return Files.readAllLines(filePath);
    }
}
