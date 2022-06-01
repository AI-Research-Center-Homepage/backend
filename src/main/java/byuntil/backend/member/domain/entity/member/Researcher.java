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
public class Researcher extends Member {
    private String research;

    @Builder
    public Researcher(String name, String major, String email, String image, String dtype, String research, Admin admin) {
        super(name, major, email, image, dtype, admin);
        this.research = research;
    }

    public void update(String research) {
        this.research = research;
    }
}
