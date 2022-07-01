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
    //board에 대한 dto도 필요

    @Builder
    public PostDto(String title, String image, String content, String boardName) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.boardName = boardName;
    }


    //TODO : Toentity를할때 값이 안넘어오면 에러가뜬다. 그래서 여기에 있는 값들은 모두 notnull처리를 해주어야할듯?
    //toEntity를 할때는 nullable인것을 제외하고는 모두 넣어야한다
    public Post toEntity() {
        Post build = Post.builder()
                .title(title)
                .image(image)
                .content(content)
                .build();
        return build;
    }
}
