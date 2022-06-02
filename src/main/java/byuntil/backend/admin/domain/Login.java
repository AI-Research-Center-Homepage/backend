package byuntil.backend.admin.domain;

import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Login {

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    //탈퇴한 회원 : true, 탈퇴x : false
    @Column(nullable = false)
    private Boolean deleted;

    public void changePw(String encodedPw){
        this.loginPw=encodedPw;
    }



}
