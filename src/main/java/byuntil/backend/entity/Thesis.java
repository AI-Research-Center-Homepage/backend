package byuntil.backend.entity;

import byuntil.backend.entity.member.Member;

import javax.persistence.*;

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

    private String ko_name;
    private String en_name;
    private String journal;
    private String publish;
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
