package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.domain.Admin;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Graduate extends Member {
    private LocalDateTime admission;

    @Builder
    public Graduate(String name, String major, String email, String image, String dtype,
                    LocalDateTime admission, String office, String fields, Admin admin) {
        super(name, major, email, image, dtype, office, fields,admin);
        this.admission = admission;
    }

    public void update(LocalDateTime admission) {
        this.admission = admission;
    }
}
