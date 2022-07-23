package byuntil.backend.research.dto.request;

import byuntil.backend.research.domain.entity.Thesis;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Setter
public class ThesisDto {
    private Long id;
    private final String title;
    private final String koName;
    private final String enName;
    private final String journal;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime publishDate;
    private final String url;
    private final String fieldName;
    private List<MemberDto> members;


    public Thesis toEntity() {
        return Thesis.builder().enName(this.enName).koName(this.koName)
                .title(this.title).journal(this.journal).publishDate(this.publishDate)
                .url(this.url).build();
    }
}
