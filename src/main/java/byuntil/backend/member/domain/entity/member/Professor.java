package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.dto.request.ProfessorSaveRequestDto;
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
    public ProfessorSaveRequestDto toDto(){
        if(getLogin()!=null){
            LoginDto loginDto = new LoginDto(getLogin().getLoginId(), getLogin().getLoginPw());
            return ProfessorSaveRequestDto.builder().loginDto(loginDto).email(getEmail()).location(getLocation()).image(getImage()).id(getId())
                    .doctorate(getDoctorate()).name(getName()).number(getNumber()).major(getMajor()).build();
        }
        else{
            return ProfessorSaveRequestDto.builder().email(getEmail()).location(getLocation()).image(getImage()).id(getId())
                    .doctorate(getDoctorate()).name(getName()).number(getNumber()).major(getMajor()).build();
        }

    }
}
