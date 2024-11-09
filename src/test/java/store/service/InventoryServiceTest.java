package store.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InventoryServiceTest {

    @Test
    void 상품이_존재할_경우_참을_반환한다() {

        assertTrue(InventoryService.isProductExistent("콜라"));
    }

    @Test
    void 존재하지_않을_상품일_경우_거짓을_반환한다() {

        assertFalse(InventoryService.isProductExistent("반창고"));
    }
}
