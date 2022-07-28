package byuntil.backend.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoginResponseDto {
    private String loginId;
    private String loginPw;
    private Boolean deleted;
}
