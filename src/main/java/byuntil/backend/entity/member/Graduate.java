package byuntil.backend.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Graduate extends Member {
    private String admission;

    @Builder
    public Graduate(Long id, String name, String major, String email, String image, String admission) {
        super(id, name, major, email, image);
        this.admission = admission;
    }
}
