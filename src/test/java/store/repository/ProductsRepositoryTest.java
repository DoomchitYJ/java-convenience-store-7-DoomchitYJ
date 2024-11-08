package store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static store.config.Congfig.PRODUCTS_FILE_PATH;

import java.util.List;
import org.junit.jupiter.api.Test;

public class ProductsRepositoryTest {

    @Test
    void products_md_파일을_읽는다() {
        // given
        ProductsRepository productsRepository = new ProductsRepository(PRODUCTS_FILE_PATH);

        // when
        List<List<String>> products;
        try {
            products = productsRepository.readProductsFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // then
        List<List<String>> expected = List.of(
                List.of("콜라","1000","10","탄산2+1"),
                List.of("콜라","1000","10","null"),
                List.of("사이다","1000","8","탄산2+1"),
                List.of("사이다","1000","7","null"),
                List.of("오렌지주스","1800","9","MD추천상품"),
                List.of("탄산수","1200","5","탄산2+1"),
                List.of("물","500","10","null"),
                List.of("비타민워터","1500","6","null"),
                List.of("감자칩","1500","5","반짝할인"),
                List.of("감자칩","1500","5","null"),
                List.of("초코바","1200","5","MD추천상품"),
                List.of("초코바","1200","5","null"),
                List.of("에너지바","2000","5","null"),
                List.of("정식도시락","6400","8","null"),
                List.of("컵라면","1700","1","MD추천상품"),
                List.of("컵라면","1700","10","null"));
        assertEquals(products, expected);
    }
}
