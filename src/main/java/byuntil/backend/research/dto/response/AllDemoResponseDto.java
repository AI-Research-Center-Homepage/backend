package byuntil.backend.research.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AllDemoResponseDto {
    private Long id;
    private String title;
    private String description;
    private String url;
}
