package store.exception;

public class StoreException extends IllegalArgumentException{
    public StoreException(final ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
