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
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;


    @Builder
    public Attach(Long id, String originFileName, String serverFileName, String filePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.filePath = filePath;
    }

    //연관관계 편의 메서드
    public void setPost(final Post post) {
        this.post = post;
        post.getAttaches().add(this);
    }

}
