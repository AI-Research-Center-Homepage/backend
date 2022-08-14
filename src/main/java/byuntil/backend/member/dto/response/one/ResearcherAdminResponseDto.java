package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResearcherAdminResponseDto extends MemberAdminResponseDto {
    @Builder
    public ResearcherAdminResponseDto(Long id, String name, String major, String email,
                                      String image, String location, LoginResponseDto loginDto) {
        super(id, name, major, email, image, location, loginDto);

    }
}
