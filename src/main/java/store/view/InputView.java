package store.view;

import static store.constant.Constant.DELIMITER;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputView {

    private static final String BUYING_VIEW = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ORDER_REGEX = "\\[\\p{L}+\\-\\d+\\]";

    public static List<Order> readOrder() {
        System.out.println(BUYING_VIEW);

        List<String> inputOrders = Arrays.stream(Console.readLine().trim().split(DELIMITER)).toList();
        List<Order> orders = new ArrayList<>();
        for (String input : inputOrders) {
            validateFormat(input);

        }

        return orders;
    }

    private static void validateFormat(String input) {
        if (!input.matches(ORDER_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
}

