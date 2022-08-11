package byuntil.backend.research.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class MemberDto {
    private Long id;
    private String name;
}
