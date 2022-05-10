package byuntil.backend.post.domain.entity;

import byuntil.backend.post.dto.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private int viewNum;

    //게시판, 프로젝트
    private String author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Attach> attaches = new ArrayList<>();


    @Builder
    public Post(Long id, String title, String content, int viewNum, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewNum = viewNum;
        this.author = author;
    }

    public void addAttaches(final Attach attach) {
        attaches.add(attach);
    }

    public void deleteAttaches() {
        attaches.clear();
    }

    public void updatePost(final PostDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.content = dto.getContent();
        this.attaches = dto.toEntity().getAttaches();
    }
}
