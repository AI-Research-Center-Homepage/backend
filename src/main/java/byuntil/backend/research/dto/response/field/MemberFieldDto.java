package byuntil.backend.research.dto.response.field;


import byuntil.backend.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class MemberFieldDto {
    private List<MemberResponseDto> members;
    private List<FieldDto> fields;
}
