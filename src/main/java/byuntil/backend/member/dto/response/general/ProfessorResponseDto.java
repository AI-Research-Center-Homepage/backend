package byuntil.backend.member.dto.response.general;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfessorResponseDto extends MemberResponseDto{
    private String doctorate;
    private String number;
    @Builder
    public ProfessorResponseDto(Long id, String name, String major, String email,
                                     String image, String doctorate, String number, String location) {
        super(id, name, major, email, image, location);
        this.doctorate = doctorate;
        this.number = number;
    }
}
