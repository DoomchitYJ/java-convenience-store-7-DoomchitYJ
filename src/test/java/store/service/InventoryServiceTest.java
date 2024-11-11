package store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;

public class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        inventoryService.resetInventory();
    }

    @Test
    void 상품이_존재할_경우_참을_반환한다() {
        assertTrue(inventoryService.isProductExistent("콜라"));
    }

    @Test
    void 존재하지_않을_상품일_경우_거짓을_반환한다() {
        assertFalse(inventoryService.isProductExistent("반창고"));
    }

    @Test
    void 프로모션과_일반_재고_수량의_합을_반환한다() {
        assertEquals(20, inventoryService.getProductQuantity("콜라"));
    }

    @Test
    void 프로모션_재고의_수량만을_반환한다() {
        assertEquals(10, inventoryService.getPromotionProductQuantity("콜라"));
    }

    @Test
    void 상품의_이름으로_적용된_프로모션의_이름을_반환한다() {
        assertEquals("탄산2+1", inventoryService.getPromotionNameByProductName("콜라"));
    }

    @Test
    void 상품의_이름으로_해당_정보가_담긴_Product_객체를_반환한다() {
        Product product = inventoryService.getProduct("콜라");
        assertEquals("콜라", product.getName());
        assertEquals(1000, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("탄산2+1", product.getPromotion());
    }

    @Test
    void 상품의_재고를_변경한다() {
        inventoryService.updateProductQuantity("콜라", 10);
        assertEquals(10, inventoryService.getProductQuantity("콜라"));
    }

    @Test
    void 프로모션_재고를_먼저_소진한다() {
        inventoryService.updateProductQuantity("콜라", 10);
        assertEquals(0, inventoryService.getPromotionProductQuantity("콜라"));
    }

    @Test
    void 재고를_리셋한다() {
        inventoryService.updateProductQuantity("콜라", 10);
        inventoryService.resetInventory();
        assertEquals(20, inventoryService.getProductQuantity("콜라"));
    }
}
