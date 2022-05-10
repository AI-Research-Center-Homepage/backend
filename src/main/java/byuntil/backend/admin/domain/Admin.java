package byuntil.backend.admin.domain;

import byuntil.backend.member.domain.entity.member.Member;

import javax.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue
    @Column(name = "ADMIN_ID")
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
