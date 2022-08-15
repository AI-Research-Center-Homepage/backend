package byuntil.backend.research.dto.response.demo;

import byuntil.backend.research.dto.response.demo.AllDemoGeneralDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DemoResponseDto {
    private final List<AllDemoGeneralDto> demos;
}