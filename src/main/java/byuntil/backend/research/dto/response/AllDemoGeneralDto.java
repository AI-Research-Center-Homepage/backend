package byuntil.backend.research.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AllDemoGeneralDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String url;
    private String participants;
}
