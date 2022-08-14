package byuntil.backend.member.dto.response.general.list;

import byuntil.backend.member.dto.response.general.ResearcherResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResearcherResponseListDto {
    private List<ResearcherResponseDto> researcher;
}
