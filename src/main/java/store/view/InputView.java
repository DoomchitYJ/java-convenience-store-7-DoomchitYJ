package store.view;

import static store.constant.Constant.DELIMITER;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class InputView {

    private static final String BUYING_VIEW = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public static List<String> readProductAndCount() {
        System.out.println(BUYING_VIEW);

        return Arrays.stream(Console.readLine().trim().split(DELIMITER))
                .toList();
    }

}

