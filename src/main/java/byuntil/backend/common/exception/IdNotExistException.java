package byuntil.backend.common.exception;

public class IdNotExistException extends RuntimeException{
    public IdNotExistException() {
        super();
    }

    public IdNotExistException(String message) {
        super(message);
    }
}
