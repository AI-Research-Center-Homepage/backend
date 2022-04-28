package byuntil.backend.post.domain.repository;

import java.time.LocalDateTime;

public interface SourcePreviewMapping {
    Long getId();

    String getTitle();

    LocalDateTime getCreateDate();
}
