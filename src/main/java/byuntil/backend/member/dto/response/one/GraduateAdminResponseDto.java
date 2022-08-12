package byuntil.backend.member.dto.response.one;

import byuntil.backend.member.dto.response.LoginResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Getter
public class GraduateAdminResponseDto extends MemberAdminResponseDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime admission;

    @Builder
    public GraduateAdminResponseDto(Long id, String name, String major, String email,
                                    String image, LocalDateTime admission, String location, LoginResponseDto loginDto) {
        super(id, name, major, email, image, location, loginDto);
        this.admission = admission;
    }
}
