package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.member.dto.response.LoginResponseDto;
import byuntil.backend.member.dto.response.one.OneGraduateResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Graduate extends Member {
    private LocalDateTime admission;

    @Builder
    public Graduate(final String name, final String major, final String email, final String image, final String dtype,
                    final LocalDateTime admission, final String location, final Login login) {
        super(name, major, email, image, dtype, location, login);
        this.admission = admission;
    }

    public void update(final LocalDateTime admission) {
        this.admission = admission;
    }

    public OneGraduateResponseDto toDto(){
        if (getLogin()!=null){
            LoginResponseDto loginDto = LoginResponseDto.builder().loginId(getLogin().getLoginId()).loginPw(getLogin().getLoginPw()).deleted(getLogin().getDeleted()).build();
            return OneGraduateResponseDto.builder().loginDto(loginDto).email(getEmail()).location(getLocation()).image(getImage()).major(getMajor()).id(getId())
                    .admission(getAdmission()).name(getName()).build();
        }
        else{
            return OneGraduateResponseDto.builder().email(getEmail()).location(getLocation()).image(getImage()).major(getMajor()).id(getId())
                    .admission(getAdmission()).name(getName()).build();
        }


    }
}
