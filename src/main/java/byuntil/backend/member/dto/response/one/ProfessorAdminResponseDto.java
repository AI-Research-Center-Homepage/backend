package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfessorAdminResponseDto extends MemberAdminResponseDto {
    private String doctorate;
    private String number;
    @Builder
    public ProfessorAdminResponseDto(Long id, String name, String major, String email,
                                     String image, String doctorate, String number, String location, LoginResponseDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.doctorate = doctorate;
        this.number = number;
    }
}
