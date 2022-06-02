package byuntil.backend.admin.controlller.domain;

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

    //탈퇴한 회원 : true, 탈퇴x : false
    @Column(nullable = false)
    private Boolean deleted;

    public void changePw(String encodedPw){
        this.loginPw=encodedPw;
    }



}
