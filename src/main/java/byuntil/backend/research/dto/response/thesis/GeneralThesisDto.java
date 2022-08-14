package byuntil.backend.research.dto.response.thesis;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GeneralThesisDto {
    private String fieldName;
    private List<GeneralOneThesisDto> theses;
}
