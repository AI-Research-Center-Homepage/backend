package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponseDto {
    private Long id;
    private String fileName;
    private String filePath;
}
