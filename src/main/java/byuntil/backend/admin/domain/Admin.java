package byuntil.backend.admin.domain;

import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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

    //탈퇴한 회원 : true, 탈퇴x : false
    @Column(nullable = false)
    private Boolean deleted;

    public void changePw(String encodedPw){
        this.loginPw=encodedPw;
    }

    public void addMember(Member member){
        this.member = member;
        member.setAdmin(this);

    }
    public AdminDto toDto(){
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(this.role.toString()));
        AdminDto dto = new AdminDto(this.loginId, this.loginPw, auth, this.deleted);
        return dto;
    }

}
