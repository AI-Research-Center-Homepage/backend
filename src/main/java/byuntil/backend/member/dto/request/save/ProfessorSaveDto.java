package byuntil.backend.member.dto.request.save;

import byuntil.backend.admin.controlller.domain.Login;
import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Professor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfessorSaveDto extends MemberSaveDto {

    private String doctorate;
    private String number;

    @Builder
    public ProfessorSaveDto(Long id, String name, String major, String email, String image, String location, LoginDto loginDto, String doctorate, String number) {
        super(id, name, major, email, image, location, loginDto);
        this.doctorate = doctorate;
        this.number = number;
    }

    public ProfessorSaveDto(final Professor professor, final LoginDto loginDto) {
        super(professor.getId(), professor.getName(), professor.getMajor(), professor.getEmail(), professor.getImage(), professor.getLocation(), loginDto);
        this.doctorate = professor.getDoctorate();
        this.number = professor.getNumber();
    }

    @Override
    public Professor toEntity() {
        Login login = null;

        if (getLoginDto()!=null){
            login = getLoginDto().toEntity();
        }
        return Professor.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .doctorate(doctorate)
                .number(number)
                .location(getLocation())
                .login(login)
                .build();
    }
}
