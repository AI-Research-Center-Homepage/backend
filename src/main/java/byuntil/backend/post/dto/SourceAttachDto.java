package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.SourcePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SourceAttachDto {
    private Long id;
    private String originFileName;
    private String serverFileName;
    private String filePath;

    private SourcePost post;

    @Builder
    public SourceAttachDto(Long id, String originFileName, String serverFileName, String filePath, SourcePost post) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.filePath = filePath;
        this.post = post;
    }

    public Attach toEntity() {
        Attach build = Attach.builder()
                .id(id)
                .originFileName(originFileName)
                .serverFileName(serverFileName)
                .filePath(filePath)
                .build();
        build.setSourcePost(post);
        return build;
    }
}
