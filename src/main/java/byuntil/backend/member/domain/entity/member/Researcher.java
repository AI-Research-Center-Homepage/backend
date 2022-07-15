package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.dto.request.ResearcherSaveRequestDto;
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

    public ResearcherSaveRequestDto toDto(){
        LoginDto loginDto = new LoginDto(getLogin().getLoginId(), getLogin().getLoginPw());
        return ResearcherSaveRequestDto.builder().loginDto(loginDto).email(getEmail()).location(getLocation()).image(getImage()).major(getMajor())
                .name(getName()).build();
    }
}
