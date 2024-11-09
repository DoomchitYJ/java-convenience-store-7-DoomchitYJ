package store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static store.config.Congfig.PRODUCTS_FILE_PATH;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.Product;

public class ProductsRepositoryTest {

    @Test
    void products_md_를_읽고_재고를_세팅한다() {
        // given
        ProductsRepository productsRepository = new ProductsRepository(PRODUCTS_FILE_PATH);

        // when
        List<Product> inventory = productsRepository.loadInventory();
        List<String> name = new ArrayList<>();
        List<Integer> price = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        List<String> promotion = new ArrayList<>();
        for (Product product : inventory) {
            name.add(product.getName());
            price.add(product.getPrice());
            quantity.add(product.getQuantity());
            promotion.add(product.getPromotion());
        }

        // then
        List<String> expectedName = List.of("콜라",
                "콜라",
                "사이다",
                "사이다",
                "오렌지주스",
                "탄산수",
                "물",
                "비타민워터",
                "감자칩",
                "감자칩",
                "초코바",
                "초코바",
                "에너지바",
                "정식도시락",
                "컵라면",
                "컵라면");
        List<Integer> expectedPrice = List.of(
                1000,
                1000,
                1000,
                1000,
                1800,
                1200,
                500,
                1500,
                1500,
                1500,
                1200,
                1200,
                2000,
                6400,
                1700,
                1700
        );
        List<Integer> expectedQuantity = List.of(
                10,
                10,
                8,
                7,
                9,
                5,
                10,
                6,
                5,
                5,
                5,
                5,
                5,
                8,
                1,
                10
        );
        List<String> expectedPromotion = List.of(
                "탄산2+1",
                "",
                "탄산2+1",
                "",
                "MD추천상품",
                "탄산2+1",
                "",
                "",
                "반짝할인",
                "",
                "MD추천상품",
                "",
                "",
                "",
                "MD추천상품",
                ""
        );
        assertEquals(expectedName, name);
        assertEquals(expectedPrice, price);
        assertEquals(expectedQuantity, quantity);
        assertEquals(expectedPromotion, promotion);
    }
}
