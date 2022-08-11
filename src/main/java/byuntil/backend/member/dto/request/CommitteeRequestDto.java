package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controller.domain.dto.LoginDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommitteeRequestDto {
    private String name;
    private String major;
    private String image;
    private LoginDto loginDto;
    private String email;
    private String location;
    private String position;
}
