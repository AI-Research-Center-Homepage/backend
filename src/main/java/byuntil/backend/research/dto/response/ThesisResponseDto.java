package byuntil.backend.research.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ThesisResponseDto<T> {
    private final List<T> theses;
}
