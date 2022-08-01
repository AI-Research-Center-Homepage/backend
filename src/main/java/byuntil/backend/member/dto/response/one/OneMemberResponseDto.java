package byuntil.backend.member.dto.response.one;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class OneMemberResponseDto {
    private Long id;
    private String name;
    private String major;
    private String email;
    private String image;
    private String location;
    private LoginResponseDto loginDto;

}
