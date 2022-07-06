package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class NoticeResponseDto {
    private final String title;
    private final String content;
    private final int viewNum;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final List<AttachResponseDto> attach;
}
