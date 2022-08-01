package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String boardName;
    private String author;
    private List<String> imageList;
    //board에 대한 dto도 필요

    @Builder
    public PostDto(Long id, String title, List<String> images, String content, String boardName, String author) {
        this.id = id;
        this.title = title;
        this.imageList = images;
        this.content = content;
        this.boardName = boardName;
        this.author = author;
    }

    public Post toEntity() {
        Post build = Post.builder()
                .title(title).author(author)
                .images(imageList).content(content)
                .build();
        return build;
    }
}
