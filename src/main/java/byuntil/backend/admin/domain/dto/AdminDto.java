package byuntil.backend.admin.domain.dto;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@ToString
public class AdminDto extends User {
    private String loginId;
    public AdminDto(String loginId, String loginPw, Collection<? extends GrantedAuthority> authorities){
        super(loginId, loginPw, authorities);
        this.loginId = loginId;
    }
    //pw는 부모의 pw를 사용하기때문에 넣지 않았다
    public Admin toEntity(){
        //ROLE_이 붙기 때문에 RETURN된 STRING을 잘랐다
        UserRole searchedValue = UserRole.valueOf(getAuthorities().toArray()[0].toString());
        return Admin.builder().loginId(this.loginId)
                .loginPw(super.getPassword())
                .role(searchedValue).build();

    }
}
