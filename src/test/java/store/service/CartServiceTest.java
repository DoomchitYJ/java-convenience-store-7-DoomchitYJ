package store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartServiceTest {

    private static final InventoryService inventoryService = new InventoryService();
    private static CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(inventoryService);
        cartService.clearCart();
    }

    @Test
    void 장바구니에_물품을_추가한다() {
        cartService.addToCart("콜라", 9, 3);
        cartService.addToCart("사이다", 1, 0);

        assertEquals(11, inventoryService.getProductQuantity("콜라"));
        assertEquals(9, cartService.getItemsQuantity().get(0));
        assertEquals(1, cartService.getItemsQuantity().get(1));
    }

    @Test
    void 장바구니를_리셋한다() {
        cartService.addToCart("콜라", 9, 3);
        cartService.clearCart();

        assertThrows(IndexOutOfBoundsException.class, () -> cartService.getItemsQuantity().get(0));
    }

}
