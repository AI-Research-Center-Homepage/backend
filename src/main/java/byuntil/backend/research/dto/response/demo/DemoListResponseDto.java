package byuntil.backend.research.dto.response.demo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DemoListResponseDto {
    private List<AllDemoResponseDto> demos;
}
