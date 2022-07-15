package byuntil.backend.common.exception;

public class TypeNotExistException extends RuntimeException{
    public TypeNotExistException() {
        super();
    }

    public TypeNotExistException(String message) {
        super(message);
    }
}
