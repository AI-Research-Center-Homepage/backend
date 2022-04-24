package byuntil.backend.common.exception.s3;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.ExceptionType;

public class InvalidExtensionException extends ApplicationException {
    public InvalidExtensionException() {
        super(ExceptionType.INVALID_EXTENSION.getHttpStatus(), ExceptionType.INVALID_EXTENSION.getDetail());
    }
}
