package store.view;

import static store.constant.Constant.DELIMITER;
import static store.exception.ExceptionMessage.INVALID_FORMAT;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.Order;
import store.exception.StoreException;

public class InputView {

    private static final String BUYING_VIEW = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ORDER_REGEX = "\\[\\p{L}+\\-\\d+\\]";

    private static final String MORE_FREE_VIEW = "현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";

    public static List<Order> readOrder() {
        System.out.println(BUYING_VIEW);

        List<String> inputOrders = Arrays.stream(Console.readLine().trim().split(DELIMITER)).toList();
        Pattern pattern = Pattern.compile(ORDER_REGEX);
        List<Order> orders = new ArrayList<>();
        for (String input : inputOrders) {
            validateFormat(input);
            String name = parseName(pattern, input);
            int quantity = parseQuantity(pattern, input);

            orders.add(new Order(name, quantity));
        }

        return orders;
    }

    private static void validateFormat(String input) {
        if (!input.matches(ORDER_REGEX)) {
            throw new StoreException(INVALID_FORMAT);
        }
    }

    private static String parseName(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        String name = matcher.group(1);

        return name;
    }

    private static int parseQuantity(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        int quantity = Integer.parseInt(matcher.group(1));

        return quantity;
    }

    public static String readMoreFree() {
        System.out.println(MORE_FREE_VIEW);

        return Console.readLine().trim();
    }
}

