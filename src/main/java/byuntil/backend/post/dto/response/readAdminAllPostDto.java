package byuntil.backend.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
public class readAdminAllPostDto {
    private Long id;
    private String title;
    private String author;
    private Integer viewNum;;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
