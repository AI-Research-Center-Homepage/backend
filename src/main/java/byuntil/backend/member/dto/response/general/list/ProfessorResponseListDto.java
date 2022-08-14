package byuntil.backend.member.dto.response.general.list;


import byuntil.backend.member.dto.response.general.ProfessorResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProfessorResponseListDto {
    private List<ProfessorResponseDto> professor;
}
