package byuntil.backend.post.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attach {
    @Id
    @GeneratedValue
    @Column(name = "ATTACH_ID")
    private Long id;

    private String originFileName;

    private String serverFileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEWS_POST_ID")
    private NewsPost newsPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTICE_POST_ID")
    private NoticePost noticePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_POST_ID")
    private SourcePost sourcePost;

    @Builder
    public Attach(Long id, String originFileName, String serverFileName, String filePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.filePath = filePath;
    }

    //연관관계 편의 메서드
    public void setNewsPost(final NewsPost newsPost) {
        this.newsPost = newsPost;
        newsPost.getAttaches().add(this);
    }

    public void setNoticePost(final NoticePost noticePost) {
        this.noticePost = noticePost;
        noticePost.addAttaches(this);
    }

    public void setSourcePost(final SourcePost sourcePost) {
        this.sourcePost = sourcePost;
        sourcePost.addAttaches(this);
    }
}
