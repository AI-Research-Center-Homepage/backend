package byuntil.backend.research.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DemoResponseDto<T> {
    private final List<T> demos;
}
