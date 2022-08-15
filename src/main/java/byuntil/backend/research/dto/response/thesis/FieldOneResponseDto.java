package byuntil.backend.research.dto.response.thesis;

import byuntil.backend.research.dto.request.MemberDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FieldOneResponseDto {
    private final String title;
    private final String koName;
    private final String enName;
    private final String journal;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime publishDate;
    private final String url;
    private final String fieldName;
    private List<MemberDto> members;

}
