package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AttachDto {
    private Long id;
    private String originFileName;
    private String serverFileName;
    private String filePath;
    private Post post;

    @Builder
    public AttachDto(Long id, String originFileName, String serverFileName, String filePath, Post post) {
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
        build.setPost(post);
        return build;
    }
}
