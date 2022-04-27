package byuntil.backend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    //post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    DUPLICATED_POST(HttpStatus.BAD_REQUEST, "이미 등록된 게시글입니다."),
    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "기간이 잘못 입력되었습니다."),

    //s3
    INVALID_FILENAME(HttpStatus.BAD_REQUEST, "파일 이름이 없습니다."),
    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "파일의 확장자가 잘못되었습니다."),
    UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String detail;

    ExceptionType(final HttpStatus httpStatus, final String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
