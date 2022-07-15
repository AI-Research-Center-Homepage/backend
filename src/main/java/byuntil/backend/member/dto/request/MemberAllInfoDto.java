package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class MemberAllInfoDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private String location;
    private LoginDto loginDto;
    private String position;
    private LocalDateTime admission;
    private String doctorate;
    private String number;
    private String research;
}
