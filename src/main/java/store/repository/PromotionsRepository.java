package store.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PromotionsRepository {

    private final Path filePath;

    public PromotionsRepository(final Path filePath) {
        this.filePath = filePath;
    }

    public List<String> readPromotionsFile() throws IOException {
        return Files.readAllLines(filePath);
    }
}
