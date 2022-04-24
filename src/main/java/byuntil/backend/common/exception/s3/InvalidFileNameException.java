package byuntil.backend.common.exception.s3;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.ExceptionType;

public class InvalidFileNameException extends ApplicationException {

    public InvalidFileNameException() {
        super(ExceptionType.INVALID_FILENAME.getHttpStatus(), ExceptionType.INVALID_FILENAME.getDetail());
    }
}
