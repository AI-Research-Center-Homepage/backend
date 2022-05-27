package byuntil.backend.common.exception;

public class LoginIdDuplicationException extends RuntimeException{
    public LoginIdDuplicationException() {
        super();
    }

    public LoginIdDuplicationException(String message) {
        super(message);
    }
}
