package store.exception;

public class StoreException extends IllegalArgumentException{
    private StoreException(final ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
