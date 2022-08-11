package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.AllArgsConstructor;
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
