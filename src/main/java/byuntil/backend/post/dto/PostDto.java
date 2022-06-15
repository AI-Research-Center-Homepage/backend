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
    private List<String> urlList;
    //board에 대한 dto도 필요

    @Builder
    public PostDto(String title, String content, BoardDto boardDto, List<String> urlList) {
        this.title = title;
        this.content = content;
        this.boardDto = boardDto;
        this.urlList = urlList;
    }


    //toEntity를 할때는 nullable인것을 제외하고는 모두 넣어야한다
    public Post toEntity() {
        Post build = Post.builder()
                .title(title)
                .content(content)
                .urlList(urlList)
                .build();
        return build;
    }
}
