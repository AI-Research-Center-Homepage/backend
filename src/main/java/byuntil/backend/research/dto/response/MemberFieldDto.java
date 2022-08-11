package byuntil.backend.research.dto.response;


import byuntil.backend.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class MemberFieldDto {
    private List<MemberResponseDto> memberDtoList;
    private List<byuntil.backend.research.dto.response.FieldDto> fieldDtoList;
}
