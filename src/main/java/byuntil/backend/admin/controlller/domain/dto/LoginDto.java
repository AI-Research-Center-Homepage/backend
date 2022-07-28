package byuntil.backend.admin.controlller.domain.dto;

import byuntil.backend.admin.controlller.domain.Login;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;

@Slf4j
@Getter
@ToString
@Setter
public class LoginDto extends User {
    private String loginId;
    private String loginPw;
    private Boolean deleted;
    //일단 초기화 해놓고 toentity할때 모든 user의 권한을 role_admin으로 변경한다
    public LoginDto(String loginId, String loginPw, Collection<SimpleGrantedAuthority> auths ,boolean deleted){
        super(loginId, loginPw, auths);
        this.loginId = loginId;
        this.deleted = deleted;
        this.loginPw = loginPw;
    }


    //기본생성자가 없으면 오류남 -> RequestBody때문에
    //일단 기본값을 setting 해두고 setter로 바꾸는 방법을 선택,
    public LoginDto(){
        super("id","pw", new ArrayList<>());
    }

    public LoginDto(String loginId, String loginPw, boolean deleted){
        super(loginId, loginPw, new ArrayList<>());
        this.loginId = loginId;
        this.deleted = deleted;
        this.loginPw = loginPw;
    }
    public LoginDto(String loginId, String loginPw){
        super(loginId, loginPw, new ArrayList<>());
        this.loginId = loginId;
        this.deleted = false;
        this.loginPw = loginPw;
    }
    //pw는 부모의 pw를 사용하기때문에 넣지 않았다
    public Login toEntity(){
        //모든 사용자의 역할을 ADMIN으로 통일..
        //Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        //auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        //UserRole searchedValue = UserRole.valueOf(auths.toArray()[0].toString());
        return Login.builder().loginId(this.loginId)
                .loginPw(loginPw)
                .deleted(deleted).build();

    }
}
