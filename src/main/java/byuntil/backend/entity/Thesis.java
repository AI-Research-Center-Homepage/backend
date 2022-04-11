package byuntil.backend.entity;

import byuntil.backend.entity.member.Member;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
public class Thesis {
    @Id @GeneratedValue
    @Column(name = "THESIS_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    private String title;

    //List로 안받나..?
    //저자명
    private String korName;

    private String engName;

    private String journal;

    //private String publish;
    private LocalDateTime publishDate;

    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
