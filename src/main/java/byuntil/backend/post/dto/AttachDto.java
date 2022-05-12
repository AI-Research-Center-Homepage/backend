package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AttachDto {
    private String originFileName;
    private String serverFileName;
    private String filePath;
    private Post post;
    @Builder
    public AttachDto(String originFileName, String serverFileName, String filePath, Post post) {
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.filePath = filePath;
        this.post = post;
    }

    public Attach toEntity() {
        Attach build = Attach.builder()
                .originFileName(originFileName)
                .serverFileName(serverFileName)
                .filePath(filePath)
                .build();
        build.setPost(post);
        return build;
    }
}
