package byuntil.backend.admin.controlller.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Login {

    //nullable을 false로 바꾸면 login id가 없는 사용자가 등록이 되지 않아서 그냥 없앴다
    @Column(unique = true)
    private String loginId;

    @Column()
    private String loginPw;

    //탈퇴한 회원 : true, 탈퇴x : false
    @Column()
    private Boolean deleted;

    public void changePw(final String encodedPw) {
        this.loginPw = encodedPw;
    }

}
