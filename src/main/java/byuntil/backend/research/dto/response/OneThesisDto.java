package byuntil.backend.research.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OneThesisDto {
    private Long id;
    private String title;
    private String journal;
    private LocalDateTime publishDate;
}
