package store.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseServiceTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private static final InventoryService inventoryService = new InventoryService();
    private static final CartService cartService = new CartService(inventoryService);
    private static PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        purchaseService = new PurchaseService(cartService);
    }

    @Test
    void  영수증을_출력하고_구매를_완료한다() {
        cartService.addToCart("콜라", 9, 3);

        String simulatedUserInput = "Y\n"; // 각 입력은 줄바꿈으로 구분
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        purchaseService.completePurchase();

        String receipt = outputStreamCaptor.toString().trim();

        assertTrue(receipt.contains("콜라"));
        assertTrue(receipt.contains("9,000"));
    }

    @Test
    void  장바구니를_비우고_구매를_완료한다() {
        cartService.addToCart("콜라", 9, 3);

        String simulatedUserInput = "Y\n"; // 각 입력은 줄바꿈으로 구분
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        purchaseService.completePurchase();

        assertTrue(cartService.getItemsQuantity().isEmpty());
    }
}
