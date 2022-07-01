package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto<T> {
    private final String boardName;
    private final List<T> posts;
}
