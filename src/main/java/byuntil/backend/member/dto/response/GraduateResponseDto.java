package byuntil.backend.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GraduateResponseDto<T> {
    private final List<T> graduate;
}
