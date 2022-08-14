package byuntil.backend.research.dto.response.thesis;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class GeneralOneThesisDto {
    private Long id;
    private String title;
    private String koName;
    private String enName;
    private String journal;
    private LocalDateTime publishDate;
    private String url;
}
