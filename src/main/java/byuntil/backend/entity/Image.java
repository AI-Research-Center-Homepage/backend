package byuntil.backend.entity;

import byuntil.backend.entity.member.Member;

import javax.persistence.*;

@Entity
public class Image {
    @Id@GeneratedValue
    @Column(name = "IMAGE_ID")
    private Long id;

    private String originName;

    private String serverName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
