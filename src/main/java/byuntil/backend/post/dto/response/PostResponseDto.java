package byuntil.backend.post.dto.response;

import byuntil.backend.post.controller.common.PostController;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {
    private final String boardName;
    private final Page<PostPreviewDto> posts;
}
