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
public class Committee extends Member {
    private String position;

    @Builder
    public Committee(String name, String major, String email, String image, String dtype, String position, Admin admin) {
        super(name, major, email, image, dtype, admin);
        this.position = position;
    }

    public void update(String position) {
        this.position = position;
    }
}
