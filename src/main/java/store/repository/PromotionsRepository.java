package store.repository;

import static store.constant.Constant.DELIMITER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PromotionsRepository {

    private static final int PROMOTIONS_START_INDEX = 1;

    private final Path filePath;

    public PromotionsRepository(final String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<List<String>> readPromotionsFile() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        String[] header = lines.getFirst().split(DELIMITER);

        List<List<String>> promotions = addPromotions(lines, header);

        return promotions;
    }

    private List<List<String>> addPromotions(List<String> lines, String[] header) {
        List<List<String>> promotions = new ArrayList<>();

        for (int i = PROMOTIONS_START_INDEX; i < lines.size(); i++) {
            String[] line = lines.get(i).split(DELIMITER);
            List<String> promotionInfo = new ArrayList<>();

            for (int j = 0; j < header.length; j++) {
                promotionInfo.add(header[j].trim());
            }
            promotions.add(promotionInfo);
        }

        return promotions;
    }
}
