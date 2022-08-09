package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfessorRequestDto {
    private String name;
    private String major;
    private String image;
    private LoginDto loginDto;
    private String email;
    private String location;
    private String doctorate;
    private String number;
}
