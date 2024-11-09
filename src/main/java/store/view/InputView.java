package store.view;

import static store.constant.Constant.DELIMITER;
import static store.constant.Constant.REPLY_NO;
import static store.constant.Constant.REPLY_YES;
import static store.exception.ExceptionMessage.INVALID_FORMAT;
import static store.exception.ExceptionMessage.NOT_YES_OR_NO;

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

    private static final String MORE_FREE_VIEW = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";

    private static final String NO_DISCOUNT_VIEW =
            "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";

    private static final String MEMBERSHIP_VIEW = "멤버십 할인을 받으시겠습니까? (Y/N)";

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

    public static String readMoreFree(String name) {
        System.out.printf(MORE_FREE_VIEW, name);

        String input = Console.readLine().trim();
        validateYesOrNo(input);
        return input;
    }

    public static String readNoDiscount(String name, int quantity) {
        System.out.printf(NO_DISCOUNT_VIEW, name, quantity);

        String input = Console.readLine().trim();
        validateYesOrNo(input);
        return input;
    }

    public static String readMembership() {
        System.out.println(MEMBERSHIP_VIEW);

        String input = Console.readLine().trim();
        validateYesOrNo(input);
        return input;
    }

    private static void validateYesOrNo(String input) {
        if (!input.equals(REPLY_YES) && !input.equals(REPLY_NO)) {
            throw new StoreException(NOT_YES_OR_NO);
        }
    }
}

