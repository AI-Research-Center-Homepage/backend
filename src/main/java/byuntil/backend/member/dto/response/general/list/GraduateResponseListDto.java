package byuntil.backend.member.dto.response.general.list;

import byuntil.backend.member.dto.response.general.GraduateResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GraduateResponseListDto {
    private List<GraduateResponseDto> graduate;
}
