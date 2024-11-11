package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.service.CartService;
import store.service.InventoryService;

public class PurchasePolicyTest {

    private static final InventoryService inventoryService = new InventoryService();
    private static final CartService cartService = new CartService(inventoryService);
    private static PurchasePolicy purchasePolicy;

    @BeforeEach
    void setUp() {
        purchasePolicy = new PurchasePolicy(inventoryService, cartService);
        cartService.clearCart();
        inventoryService.resetInventory();
    }

    @Test
    void 상품을_장바구니에_넣는다() {

        Order order = new Order("콜라", 9);
        purchasePolicy.buy(order);

        assertEquals(9, cartService.getItemsQuantity().get(0));
    }

    @Test
    void 프로모션_적용_가능_상품인데_수량만큼_가져오지_않은_경우_Y() {
        Order order = new Order("콜라", 2);

        String simulatedUserInput = "Y\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        purchasePolicy.buy(order);

        assertEquals(3, cartService.getItemsQuantity().get(0));
        assertEquals(1, cartService.getItemsFree().get(0));
    }

    @Test
    void 프로모션_적용_가능_상품인데_수량만큼_가져오지_않은_경우_N() {
        Order order = new Order("콜라", 2);

        String simulatedUserInput = "N\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        purchasePolicy.buy(order);

        assertEquals(2, cartService.getItemsQuantity().get(0));
        assertEquals(0, cartService.getItemsFree().get(0));
    }

    @Test
    void 프로모션_재고가_부족하여_혜택_없이_결제해야_하는_경우_Y() {
        Order order = new Order("콜라", 11);

        String simulatedUserInput = "Y\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        purchasePolicy.buy(order);

        assertEquals(11, cartService.getItemsQuantity().get(0));
    }

    @Test
    void 프로모션_재고가_부족하여_혜택_없이_결제해야_하는_경우_N() {
        Order order = new Order("콜라", 11);

        String simulatedUserInput = "N\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        purchasePolicy.buy(order);

        assertEquals(9, cartService.getItemsQuantity().get(0));
    }
}
