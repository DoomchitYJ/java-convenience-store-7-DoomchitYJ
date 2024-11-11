package store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static store.config.Congfig.PROMOTIONS_FILE_PATH;
import static store.constant.Constant.DATE_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

public class PromotionsRepositoryTest {

    @Test
    void promotions_md_를_읽고_재고를_세팅한다() {
        // given
        PromotionsRepository promotionsRepository = new PromotionsRepository(PROMOTIONS_FILE_PATH);

        // when
        List<Promotion> promotions = promotionsRepository.loadPromotions();

        List<String> name = promotions.stream().map(Promotion::getName).toList();
        List<Integer> buy = promotions.stream().map(Promotion::getBuy).toList();
        List<Integer> get = promotions.stream().map(Promotion::getGet).toList();
        List<LocalDateTime> startDate = promotions.stream().map(Promotion::getStartDate).toList();
        List<LocalDateTime> endDate = promotions.stream().map(Promotion::getEndDate).toList();

        // then
        List<String> expectedName = List.of("탄산2+1", "MD추천상품", "반짝할인");
        List<Integer> expectedBuy = List.of(2, 1, 1);
        List<Integer> expectedGet = List.of(1, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        List<LocalDateTime> expectedStartDate = List.of(LocalDate.parse("2024-01-01", formatter).atStartOfDay(),
                LocalDate.parse("2024-01-01", formatter).atStartOfDay(),
                LocalDate.parse("2024-11-01", formatter).atStartOfDay());
        List<LocalDateTime> expectedEndDate = List.of(LocalDate.parse("2024-12-31", formatter).atStartOfDay(),
                LocalDate.parse("2024-12-31", formatter).atStartOfDay(),
                LocalDate.parse("2024-11-30", formatter).atStartOfDay());

        assertEquals(expectedName, name);
        assertEquals(expectedBuy, buy);
        assertEquals(expectedGet, get);
        assertEquals(expectedStartDate, startDate);
        assertEquals(expectedEndDate, endDate);
    }
}
