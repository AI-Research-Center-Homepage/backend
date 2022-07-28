package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class MemberAllInfoDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private String location;
    private LoginDto loginDto;
    private String position;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime admission;
    private String doctorate;
    private String number;
    private String research;
}
