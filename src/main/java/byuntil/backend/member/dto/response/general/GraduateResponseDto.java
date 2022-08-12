package byuntil.backend.member.dto.response.general;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class GraduateResponseDto extends MemberResponseDto{
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime admission;

    @Builder
    public GraduateResponseDto(Long id, String name, String major, String email,
                                    String image, LocalDateTime admission, String location) {
        super(id, name, major, email, image, location);
        this.admission = admission;
    }
}
