package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.SourcePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SourcePostDto {
    private Long id;
    private String author;
    private String title;
    private String content;

    @Builder
    public SourcePostDto(Long id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public SourcePost toEntity() {
        SourcePost build = SourcePost.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }
}
