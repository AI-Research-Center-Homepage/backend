package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.Text;

@Getter
public class PostDto {
    private Long id;
    private String author;
    private String title;
    private String content;

    @Builder
    public PostDto(Long id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }


    public Post toEntity() {
        Post build = Post.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }
}
