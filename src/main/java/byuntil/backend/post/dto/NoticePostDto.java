package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.NoticePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticePostDto {
    private Long id;
    private String author;
    private String title;
    private String content;

    @Builder
    public NoticePostDto(Long id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public NoticePost toEntity() {
        NoticePost build = NoticePost.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }
}
