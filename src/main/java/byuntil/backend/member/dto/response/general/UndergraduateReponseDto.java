package byuntil.backend.member.dto.response.general;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class UndergraduateReponseDto extends MemberResponseDto{
    private int admission;
    @Builder
    public UndergraduateReponseDto(Long id, String name, String major, int admission,
                                         String email, String image, String location) {
        super(id, name, major, email, image, location);
        this.admission = admission;
    }
}
