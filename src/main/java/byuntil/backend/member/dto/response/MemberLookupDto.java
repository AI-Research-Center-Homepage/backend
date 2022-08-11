package byuntil.backend.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class MemberLookupDto {
    private Long id;
    private String name;
    private String major;
    private String email;
}
