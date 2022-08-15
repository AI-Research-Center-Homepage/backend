package byuntil.backend.research.dto.response.thesis;

import byuntil.backend.research.dto.response.thesis.AllThesisResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class findAllInfoDto {
    private List<AllThesisResponseDto> fields;
}
