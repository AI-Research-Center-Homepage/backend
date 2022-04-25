package byuntil.backend.member.domain.entity;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.research.domain.Thesis;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Member_Thesis {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_THESIS_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "THESIS_ID")
    private Thesis thesis;
}