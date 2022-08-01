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

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String serverFileName;

    @Column(nullable = false)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;


    @Builder
    public Attach(final Long id, final String originFileName, final String serverFileName, final String filePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.fileUrl = fileUrl;
    }

    //연관관계 편의 메서드
    public void addPost(final Post post) {
        this.post = post;
        post.getAttaches().add(this);
    }

}
