package byuntil.backend.member.dto.request;

import byuntil.backend.member.domain.entity.member.Professor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfessorSaveRequestDto extends MemberSaveRequestDto {

    private String doctorate;
    private String number;

    @Builder
    public ProfessorSaveRequestDto(String name, String major, String email, String image,
                                   String doctorate, String office, String number, String fields) {
        super(name, major, email, image, office, fields);
        this.doctorate = doctorate;
        this.number = number;
    }


    @Override
    public Professor toEntity() {
        return Professor.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .doctorate(doctorate)
                .number(number)
                .office(getOffice())
                .build();
    }
}
