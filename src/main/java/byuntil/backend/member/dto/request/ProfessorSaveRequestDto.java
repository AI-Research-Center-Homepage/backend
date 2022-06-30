package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Professor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfessorSaveRequestDto extends MemberSaveRequestDto {

    private String doctorate;
    private String number;

    @Builder
    public ProfessorSaveRequestDto(String name, String major, String email, String image, String office, String fields, LoginDto loginDto, String doctorate, String number) {
        super(name, major, email, image, office, fields, loginDto);
        this.doctorate = doctorate;
        this.number = number;
    }

    public ProfessorSaveRequestDto(final Professor professor, final LoginDto loginDto) {
        super(professor.getName(), professor.getMajor(), professor.getEmail(), professor.getImage(), professor.getOffice(), professor.getFields(), loginDto);
        this.doctorate = professor.getDoctorate();
        this.number = professor.getNumber();
    }

    @Override
    public Professor toEntity() {
        return Professor.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .doctorate(doctorate)
                .fields(getFields())
                .number(number)
                .office(getOffice())
                .login(getLoginDto().toEntity())
                .build();
    }
}
