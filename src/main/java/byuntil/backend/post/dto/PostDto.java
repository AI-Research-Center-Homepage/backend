package byuntil.backend.post.dto;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {
    private String title;
    private String content;
    private BoardDto boardDto;
    //board에 대한 dto도 필요

    @Builder
    public PostDto(String title, String content, BoardDto boardDto) {
        this.title = title;
        this.content = content;
        this.boardDto = boardDto;
    }


    //TODO : Toentity를할때 값이 안넘어오면 에러가뜬다. 그래서 여기에 있는 값들은 모두 notnull처리를 해주어야할듯?
    //toEntity를 할때는 nullable인것을 제외하고는 모두 넣어야한다
    public Post toEntity() {
        Post build = Post.builder()
                .title(title)
                .content(content)
                .build();
        return build;
    }
}