package byuntil.backend.common.exception.post;

import byuntil.backend.common.exception.ApplicationException;
import byuntil.backend.common.exception.ExceptionType;

public class PostNotFoundException extends ApplicationException {
    public PostNotFoundException() {
        super(ExceptionType.POST_NOT_FOUND.getHttpStatus(), ExceptionType.POST_NOT_FOUND.getDetail());
    }
}
