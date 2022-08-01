package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String image;
    private String content;
    private String boardName;
    private String author;
    //board에 대한 dto도 필요

    @Builder
    public PostDto(Long id, String title, String image, String content, String boardName, String author) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.boardName = boardName;
        this.author = author;
    }


    //toEntity를 할때는 nullable인것을 제외하고는 모두 넣어야한다
    public Post toEntity() {
        Post build = Post.builder()
                .title(title)
                .image(image)
                .author(author)
                .content(content)
                .build();
        return build;
    }
}
