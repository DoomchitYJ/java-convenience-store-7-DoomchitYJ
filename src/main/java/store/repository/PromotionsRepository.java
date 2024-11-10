package store.repository;

import static store.constant.Constant.CONTENTS_START_INDEX;
import static store.constant.Constant.DATE_FORMAT;
import static store.constant.Constant.DELIMITER;
import static store.constant.Constant.INDEX_BUY;
import static store.constant.Constant.INDEX_END_DATE;
import static store.constant.Constant.INDEX_GET;
import static store.constant.Constant.INDEX_NAME;
import static store.constant.Constant.INDEX_START_DATE;
import static store.exception.ExceptionMessage.FILE_PATH_ERROR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import store.domain.Promotion;
import store.exception.StoreException;

public class PromotionsRepository {

    private static final int PROMOTIONS_START_INDEX = 1;

    private final Path filePath;

    public PromotionsRepository(final String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Promotion> loadPromotions() {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Promotion> promotions = new ArrayList<>();
            for (int i = CONTENTS_START_INDEX; i < lines.size(); i++) {
                String[] promotion = lines.get(i).split(DELIMITER);
                promotions.add(convertIntoPromotion(promotion));
            }
            return promotions;
        } catch (IOException e) {
            throw new StoreException(FILE_PATH_ERROR);
        }
    }

    private static Promotion convertIntoPromotion(String[] content) {
        String name = content[INDEX_NAME];
        int buy = Integer.parseInt(content[INDEX_BUY]);
        int get = Integer.parseInt(content[INDEX_GET]);
        LocalDateTime startDate = getDate(content[INDEX_START_DATE]);
        LocalDateTime endDate = getDate(content[INDEX_END_DATE]);

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static LocalDateTime getDate(String dateInString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate localDate = LocalDate.parse(dateInString, formatter);
        return localDate.atStartOfDay();
    }
}
