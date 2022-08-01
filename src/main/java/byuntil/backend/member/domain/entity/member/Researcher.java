package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.dto.request.ResearcherSaveRequestDto;
import byuntil.backend.member.dto.response.LoginResponseDto;
import byuntil.backend.member.dto.response.one.OneResearcherResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Researcher extends Member {

    @Builder
    public Researcher(final String name, final String major, final String email, final String image,
                      String dtype, String location, Login login) {
        super(name, major, email, image, dtype, location, login);
    }

    public OneResearcherResponseDto toDto(){
        if(getLogin()!=null){
            LoginResponseDto loginDto = LoginResponseDto.builder().loginId(getLogin().getLoginId()).loginPw(getLogin().getLoginPw()).deleted(getLogin().getDeleted()).build();
            return OneResearcherResponseDto.builder().loginDto(loginDto).email(getEmail()).location(getLocation()).image(getImage()).major(getMajor()).id(getId())
                    .name(getName()).build();
        }
        else{
            return OneResearcherResponseDto.builder().email(getEmail()).location(getLocation()).image(getImage()).major(getMajor()).id(getId())
                    .name(getName()).build();
        }

    }
}
