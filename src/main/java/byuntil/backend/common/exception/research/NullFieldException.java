package byuntil.backend.common.exception.research;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.ExceptionType;

public class NullFieldException extends RuntimeException {
    public NullFieldException() {
        super();
    }

    public NullFieldException(String message) {
        super(message);
    }
}
