package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Professor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfessorSaveRequestDto extends MemberSaveRequestDto {

    private String doctorate;
    private String location;
    private String number;

    @Builder
    public ProfessorSaveRequestDto(String name, String major, String email, String image, String doctorate, String location, String number, AdminDto adminDto) {
        super(name, major, email, image, adminDto);
        this.doctorate = doctorate;
        this.location = location;
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
                .location(location)
                .number(number)
                .build();
    }
}
