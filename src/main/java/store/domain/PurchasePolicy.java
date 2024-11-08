package store.domain;

import static store.config.Congfig.PROMOTIONS_FILE_PATH;
import static store.constant.Constant.DATE_FORMAT;
import static store.constant.Constant.INDEX_BUY;
import static store.constant.Constant.INDEX_END_DATE;
import static store.constant.Constant.INDEX_GET;
import static store.constant.Constant.INDEX_NAME;
import static store.constant.Constant.INDEX_START_DATE;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import store.repository.PromotionsRepository;

public class PurchasePolicy {

    private static final List<Promotion> promotions = loadPromotions();

    public static boolean isPromotionValid(Product product) {
        Promotion promotion = promotions.stream()
                .filter(p -> p.getName().equals(product.getPromotion()))
                .findFirst().orElse(null);
        Date today = new Date();
        return today.after(promotion.getStartDate()) && today.before(promotion.getEndDate());
    }

    private static List<Promotion> loadPromotions() {
        PromotionsRepository promotionsRepository = new PromotionsRepository(PROMOTIONS_FILE_PATH);
        List<Promotion> promotions = new ArrayList<>();
        try {
            List<List<String>> promotionsFileContents = promotionsRepository.readPromotionsFile();
            for (List<String> content : promotionsFileContents) {
                Promotion promotion = convertIntoPromotion(content);
                promotions.add(promotion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return promotions;
    }

    private static Promotion convertIntoPromotion(List<String> content) {
        String name = content.get(INDEX_NAME);
        int buy = Integer.parseInt(content.get(INDEX_BUY));
        int get = Integer.parseInt(content.get(INDEX_GET));
        Date startDate = getDate(content.get(INDEX_START_DATE));
        Date endDate = getDate(content.get(INDEX_END_DATE));

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static Date getDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return(formatter.parse(dateInString));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
