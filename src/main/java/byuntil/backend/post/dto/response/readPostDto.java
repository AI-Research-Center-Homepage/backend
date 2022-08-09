package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class readPostDto {
    private final String title;
    private final String content;
    private final String author;
    private final Integer viewNum;
    private final List<AttachResponseDto> attaches;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime modifiedDate;
}
