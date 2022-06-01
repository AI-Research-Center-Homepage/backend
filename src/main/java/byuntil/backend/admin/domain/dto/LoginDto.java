package byuntil.backend.admin.domain.dto;

import byuntil.backend.admin.domain.Login;
import byuntil.backend.admin.domain.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Slf4j
@Getter
@Setter
@ToString
public class LoginDto extends User {
    private String loginId;
    private String loginPw;
    private boolean deleted;
    public LoginDto(String loginId, String loginPw, Collection<? extends GrantedAuthority> authorities, boolean deleted){
        super(loginId, loginPw, authorities);
        this.loginId = loginId;
        this.deleted = deleted;
        this.loginPw = loginPw;
    }
    //pw는 부모의 pw를 사용하기때문에 넣지 않았다
    public Login toEntity(){
        //ROLE_이 붙기 때문에 RETURN된 STRING을 잘랐다
        UserRole searchedValue = UserRole.valueOf(getAuthorities().toArray()[0].toString());
        return Login.builder().loginId(this.loginId)
                .loginPw(loginPw)
                .role(searchedValue).deleted(deleted).build();

    }
}
