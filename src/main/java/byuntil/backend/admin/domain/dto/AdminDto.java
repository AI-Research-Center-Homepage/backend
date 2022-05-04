package byuntil.backend.admin.domain.dto;

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
public class AdminDto extends User {
    private String loginId;
    public AdminDto(String loginId, String loginPw, Collection<? extends GrantedAuthority> authorities){
        super(loginId, loginPw, authorities);
    }
    //pw는 부모의 pw를 사용하기때문에 넣지 않았다
}
