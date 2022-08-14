package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FileListResponseDto {
    private List<FileResponseDto> files;
}
