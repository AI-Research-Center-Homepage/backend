package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controller.domain.Login;
import byuntil.backend.member.dto.response.LoginResponseDto;
import byuntil.backend.member.dto.response.one.UndergraduateAdminResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Undergraduate extends Member {
    private LocalDateTime admission;

    @Builder
    public Undergraduate(final String name, final String major, final String email, final String image, final String dtype,
                         LocalDateTime admission, String location, Login login) {
        super(name, major, email, image, dtype, location, login);
        this.admission = admission;
    }

    public void update(final LocalDateTime admission) {
        this.admission = admission;
    }

    public UndergraduateAdminResponseDto toDto(){
        if(getLogin()!=null){
            LoginResponseDto loginDto = LoginResponseDto.builder().loginId(getLogin().getLoginId()).loginPw(getLogin().getLoginPw()).deleted(getLogin().getDeleted()).build();
            return UndergraduateAdminResponseDto.builder().email(getEmail()).loginDto(loginDto).location(getLocation()).image(getImage()).admission(getAdmission()).id(getId())
                    .name(getName()).major(getMajor()).build();
        }
        else{
            return UndergraduateAdminResponseDto.builder().email(getEmail()).location(getLocation()).image(getImage())
                    .admission(getAdmission()).id(getId())
                    .name(getName()).major(getMajor()).build();
        }

    }
}
