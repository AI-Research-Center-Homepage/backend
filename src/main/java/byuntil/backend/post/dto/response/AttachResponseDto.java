package byuntil.backend.post.dto.response;

import byuntil.backend.post.domain.entity.Attach;
import lombok.Getter;

@Getter
public class AttachResponseDto {
    private final String fileName;
    private final String filePath;

    public AttachResponseDto(final Attach attach) {
        this.fileName = attach.getOriginFileName();
        this.filePath = attach.getFileUrl();
    }
}

