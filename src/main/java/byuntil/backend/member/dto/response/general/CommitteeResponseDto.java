package byuntil.backend.member.dto.response.general;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommitteeResponseDto extends MemberResponseDto{
    private String position;

    @Builder
    public CommitteeResponseDto(Long id, String name, String major, String email,
                                     String image, String position, String location) {
        super(id, name, major, email, image, location);
        this.position = position;
    }
}
