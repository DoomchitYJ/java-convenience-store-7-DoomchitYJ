package store.exception;

public enum ExceptionMessage {

    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXISTENT_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    TOO_MANY_ORDER("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_YES_OR_NO("Y 혹은 N을 입력해주세요."),
    MAX_TRY_ERROR("최대 입력 가능 횟수를 초과했습니다."),
    FILE_PATH_ERROR("파일의 경로를 찾을 수 없습니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final String message;

    ExceptionMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
