package byuntil.backend.post.domain.entity;

import byuntil.backend.post.dto.SourcePostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SourcePost extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "SOURCE_POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private int viewNum;

    //게시판, 프로젝트
    private String author;

    @OneToMany(mappedBy = "sourcePost", cascade = CascadeType.ALL)
    private List<Attach> attaches = new ArrayList<>();

    @Builder
    public SourcePost(Long id, String title, String content, int viewNum, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewNum = viewNum;
        this.author = author;
    }

    public void addAttaches(Attach attach) {
        attaches.add(attach);
    }

    public void deleteAttaches() {
        attaches.clear();
    }

    public void updatePost(final SourcePostDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.content = dto.getContent();
        this.attaches = dto.toEntity().getAttaches();
    }
}
