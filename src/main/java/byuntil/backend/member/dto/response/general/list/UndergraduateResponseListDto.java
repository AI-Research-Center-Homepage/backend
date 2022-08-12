package byuntil.backend.member.dto.response.general.list;

import byuntil.backend.member.dto.response.general.UndergraduateReponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UndergraduateResponseListDto {
    private List<UndergraduateReponseDto> undergraduate;
}
