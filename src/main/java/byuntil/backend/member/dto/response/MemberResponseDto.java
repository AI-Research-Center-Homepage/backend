package byuntil.backend.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MemberResponseDto {
    private Long id;
    private String name;
}
