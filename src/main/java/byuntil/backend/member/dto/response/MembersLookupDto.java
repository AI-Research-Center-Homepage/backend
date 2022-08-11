package byuntil.backend.member.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class MembersLookupDto {
    private List<MemberLookupDto> members;
}
