package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.domain.Admin;
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
    public Graduate(String name, String major, String email, String image, String dtype, String admission, Admin admin) {
        super(name, major, email, image, dtype, admin);
        this.admission = admission;
    }

    public void update(String admission) {
        this.admission = admission;
    }
}
