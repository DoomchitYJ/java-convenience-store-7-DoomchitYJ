package store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.Product;

public class InventoryServiceTest {

    @Test
    void 최초_재고_셋업을_수행한다() {

        // given & when
        List<Product> inventory = InventoryService.setUpInventory();
        String name1 = inventory.getFirst().getName();
        int price1 = inventory.getFirst().getPrice();
        int quantity1 = inventory.getFirst().getQuantity();
        String promotion1 = inventory.getFirst().getPromotion();

        String name2 = inventory.get(3).getName();
        int price2 = inventory.get(3).getPrice();
        int quantity2 = inventory.get(3).getQuantity();
        String promotion2 = inventory.get(3).getPromotion();

        // then
        assertEquals("콜라", name1);
        assertEquals(1000, price1);
        assertEquals(10, quantity1);
        assertEquals("탄산2+1", promotion1);

        assertEquals("사이다", name2);
        assertEquals(1000, price2);
        assertEquals(7, quantity2);
        assertEquals(null, promotion2);
    }
}
