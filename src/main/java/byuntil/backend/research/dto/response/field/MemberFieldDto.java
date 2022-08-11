package byuntil.backend.research.dto.response.field;


import byuntil.backend.member.dto.response.MemberResponseDto;
import byuntil.backend.research.dto.response.field.FieldDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class MemberFieldDto {
    private List<MemberResponseDto> memberDtoList;
    private List<FieldDto> fieldDtoList;
}
