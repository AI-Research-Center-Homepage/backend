package byuntil.backend.post.domain;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.s3.domain.Image;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    //멤버, 게시판, 프로젝트
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BORAD_ID")
    private Board board;

    private LocalDateTime uploadDate;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private int viewNum;

    @OneToMany(mappedBy = "post")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Attach> attaches = new ArrayList<>();
}
