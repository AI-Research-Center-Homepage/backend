package byuntil.backend.entity;

import byuntil.backend.entity.member.Member;

import javax.persistence.*;

@Entity
public class Attach {
    @Id
    @GeneratedValue
    @Column(name = "ATTACH_ID")
    private Long id;

    private String originName;

    private String serverName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

}
