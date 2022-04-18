package byuntil.backend.member.domain.entity.member;

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
    public Graduate(Long id, String name, String major, String email, String image, String dtype, String admission) {
        super(id, name, major, email, image, dtype);
        this.admission = admission;
    }

    public void update(String admission) {
        this.admission = admission;
    }
}
