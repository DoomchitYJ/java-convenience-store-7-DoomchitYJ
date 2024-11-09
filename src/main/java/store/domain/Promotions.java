package store.domain;

import static store.config.Congfig.PROMOTIONS_FILE_PATH;

import java.util.List;
import store.repository.PromotionsRepository;

public class Promotions {

    private final PromotionsRepository promotionRepository;

    private static List<Promotion> promotions;

    public Promotions() {
        promotionRepository = new PromotionsRepository(PROMOTIONS_FILE_PATH);
        this.promotions = promotionRepository.loadPromotions();
    }

    public Promotion findPromotionByName(String promotionName) {
        return promotions.stream()
                .filter(p -> p.getName().equals(promotionName))
                .findFirst().orElse(null);
    }
}
