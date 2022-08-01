package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;

public class OneResearcherResponseDto extends OneMemberResponseDto{
    @Builder
    public OneResearcherResponseDto(Long id, String name, String major, String email,
                                   String image, String location, LoginResponseDto loginDto) {
        super(id, name, major, email, image, location, loginDto);

    }
}
