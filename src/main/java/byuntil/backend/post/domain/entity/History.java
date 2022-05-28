package byuntil.backend.post.domain.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class History {
    @Id
    @GeneratedValue
    @Column(name = "HISTORY_ID")
    private Long id;

    private LocalDateTime time;

    @Column(columnDefinition = "LONGTEXT")
    private String content;
}
