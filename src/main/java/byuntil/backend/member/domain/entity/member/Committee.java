package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controller.domain.Login;
import byuntil.backend.member.dto.response.LoginResponseDto;
import byuntil.backend.member.dto.response.one.OneCommitteeResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Committee extends Member {
    private String position;

    @Builder
    public Committee(final String name, final String major, final String email, final String image, final Login login,
                     final String dtype, final String position, final String location) {
        super(name, major, email, image, dtype, location, login);
        this.position = position;
    }

    public void update(final String position) {
        this.position = position;
    }

    public OneCommitteeResponseDto toDto(){
        if (getLogin()!=null){
            LoginResponseDto loginDto = LoginResponseDto.builder().loginId(getLogin().getLoginId()).loginPw(getLogin().getLoginPw()).deleted(getLogin().getDeleted()).build();
            return OneCommitteeResponseDto.builder().loginDto(loginDto).email(getEmail()).location(getLocation()).image(getImage()).id(getId())
                    .name(getName()).major(getMajor()).position(getPosition()).image(getImage()).build();
        }
        else{
            return OneCommitteeResponseDto.builder().email(getEmail()).location(getLocation()).image(getImage()).id(getId())
                    .name(getName()).major(getMajor()).position(getPosition()).image(getImage()).build();
        }
    }
}
