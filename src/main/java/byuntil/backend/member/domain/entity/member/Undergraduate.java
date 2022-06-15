package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
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

    @Builder
    public Undergraduate(String name, String major, String email, String image, String dtype,
                         LocalDateTime admission, String office, String fields, Login login) {
        super(name, major, email, image, dtype, office, fields, login);
        this.admission = admission;
    }

    public void update(LocalDateTime admission) {
        this.admission = admission;
    }
}
