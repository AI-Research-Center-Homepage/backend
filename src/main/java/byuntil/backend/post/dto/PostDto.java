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
    private List<String> imageList;
    //board에 대한 dto도 필요

    @Builder
    public PostDto(String title, List<String> imageList, String content, String boardName) {
        this.title = title;
        this.imageList = imageList;
        this.content = content;
        this.boardName = boardName;
    }


    //toEntity를 할때는 nullable인것을 제외하고는 모두 넣어야한다
    public Post toEntity() {
        Post build = Post.builder()
                .title(title)
                .images(imageList).content(content)
                .build();
        return build;
    }
}
