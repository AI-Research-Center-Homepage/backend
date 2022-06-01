package byuntil.backend.member.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Undergraduate extends Member {
    private LocalDateTime admission;
    private String research;

    @Builder
    public Undergraduate(String name, String major, String email, String image, String dtype,
                         LocalDateTime admission, String research, String office, String fields) {
        super(name, major, email, image, dtype, office, fields);
        this.admission = admission;
        this.research = research;
    }

    public void update(LocalDateTime admission, String research) {
        this.admission = admission;
        this.research = research;
    }
}
