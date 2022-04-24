package byuntil.backend.post.domain.entity;

import byuntil.backend.post.dto.NoticePostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class NoticePost extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "NOTICE_POST_ID")
    private Long id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private int viewNum;

    //게시판, 프로젝트
    private String author;

    @OneToMany(mappedBy = "noticePost", cascade = CascadeType.ALL)
    private List<Attach> attaches = new ArrayList<>();

    @Builder
    public NoticePost(Long id, String title, String content, int viewNum, String author) {
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

    public void updatePost(final NoticePostDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.content = dto.getContent();
        this.attaches = dto.toEntity().getAttaches();
    }
}
