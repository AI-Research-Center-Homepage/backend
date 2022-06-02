package byuntil.backend.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class History {
    private LocalDateTime time;
    private String content;
}
