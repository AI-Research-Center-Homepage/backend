package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controller.domain.dto.LoginDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
public class UndergraduateRequestDto {
    private String name;
    private String major;
    private String image;
    private LoginDto loginDto;
    private String email;
    private String location;
    private int admission;
}
