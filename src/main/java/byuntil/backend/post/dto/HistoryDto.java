package byuntil.backend.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDto {
    private Long id;
    private LocalDateTime time;
    private String content;
}
