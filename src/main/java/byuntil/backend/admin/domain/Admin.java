package byuntil.backend.admin.domain;

import byuntil.backend.member.domain.entity.member.Member;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue
    @Column(name = "ADMIN_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void changePw(String encodedPw){
        this.loginPw=encodedPw;
    }

    public void addMember(Member member){
        this.member = member;
        member.setAdmin(this);

    }

}
