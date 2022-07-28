package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class readPostDto {
    private final String title;
    private final String content;
    private final int viewNum;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
}
