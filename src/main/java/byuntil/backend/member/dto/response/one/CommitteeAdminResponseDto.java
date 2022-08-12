package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommitteeAdminResponseDto extends MemberAdminResponseDto {
    private String position;

    @Builder
    public CommitteeAdminResponseDto(Long id, String name, String major, String email,
                                     String image, String position, String location, LoginResponseDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.position = position;
    }

}
