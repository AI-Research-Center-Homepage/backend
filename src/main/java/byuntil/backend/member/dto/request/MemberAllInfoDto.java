package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controller.domain.dto.LoginDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class MemberAllInfoDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private String location;
    private LoginDto loginDto;
    private String position;
    private int admission;
    private String doctorate;
    private String number;
    private String research;

}
