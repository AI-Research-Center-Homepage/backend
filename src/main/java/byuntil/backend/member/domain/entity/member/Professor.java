package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controller.domain.Login;
import byuntil.backend.member.dto.response.LoginResponseDto;
import byuntil.backend.member.dto.response.one.OneProfessorResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends Member {
    @Column(nullable = false)
    private String doctorate;
    private String number;

    @Builder
    public Professor(final String name, final String major, final String email, final String image, final String dtype,
                     final String doctorate, final String location, final String number, final Login login) {
        super(name, major, email, image, dtype, location, login);
        this.doctorate = doctorate;
        this.number = number;
    }

    public void update(final String doctorate, final String number) {
        this.doctorate = doctorate;
        this.number = number;
    }
    public OneProfessorResponseDto toDto(){
        if(getLogin()!=null){
            LoginResponseDto loginDto = LoginResponseDto.builder().loginId(getLogin().getLoginId()).loginPw(getLogin().getLoginPw()).deleted(getLogin().getDeleted()).build();
            return OneProfessorResponseDto.builder().loginDto(loginDto).email(getEmail()).location(getLocation()).image(getImage()).id(getId())
                    .doctorate(getDoctorate()).name(getName()).number(getNumber()).major(getMajor()).build();
        }
        else{
            return OneProfessorResponseDto.builder().email(getEmail()).location(getLocation()).image(getImage()).id(getId())
                    .doctorate(getDoctorate()).name(getName()).number(getNumber()).major(getMajor()).build();
        }

    }
}
