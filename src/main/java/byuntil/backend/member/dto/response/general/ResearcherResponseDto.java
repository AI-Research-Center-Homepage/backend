package byuntil.backend.member.dto.response.general;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResearcherResponseDto extends MemberResponseDto{
    @Builder
    public ResearcherResponseDto(Long id, String name, String major, String email,
                                      String image, String location) {
        super(id, name, major, email, image, location);

    }
}
