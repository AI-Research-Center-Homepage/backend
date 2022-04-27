package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.NewsPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NewsAttachDto {
    private Long id;
    private String originFileName;
    private String serverFileName;
    private String filePath;
    private NewsPost post;

    @Builder
    public NewsAttachDto(Long id, String originFileName, String serverFileName, String filePath, NewsPost post) {
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
        build.setNewsPost(post);
        return build;
    }
}
