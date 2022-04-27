package byuntil.backend.common.exception.s3;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.ExceptionType;

public class UploadFailException extends ApplicationException {
    public UploadFailException() {
        super(ExceptionType.UPLOAD_FAIL.getHttpStatus(), ExceptionType.UPLOAD_FAIL.getDetail());
    }
}
