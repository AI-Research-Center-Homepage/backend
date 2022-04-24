package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.NoticePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeAttachDto {
    private Long id;
    private String originFileName;
    private String serverFileName;
    private String filePath;

    private NoticePost post;

    @Builder
    public NoticeAttachDto(Long id, String originFileName, String serverFileName, String filePath, NoticePost post) {
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
        build.setNoticePost(post);
        return build;
    }
}
