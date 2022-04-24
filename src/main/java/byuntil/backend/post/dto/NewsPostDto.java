package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.NewsPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NewsPostDto {
    private Long id;
    private String author;
    private String title;
    private String content;

    @Builder
    public NewsPostDto(Long id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }


    public NewsPost toEntity() {
        NewsPost build = NewsPost.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }
}
