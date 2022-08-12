package byuntil.backend.member.dto.response.general.list;

import byuntil.backend.member.dto.response.general.CommitteeResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder

public class CommitteeResponseListDto {
    private List<CommitteeResponseDto> committee;
}
