package byuntil.backend.common.exception;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException() {
        super();
    }

    public BoardNotFoundException(String message) {
        super(message);
    }
}
