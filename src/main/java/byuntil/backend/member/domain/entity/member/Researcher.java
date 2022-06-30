package byuntil.backend.member.domain.entity.member;

import byuntil.backend.admin.controlller.domain.Login;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Researcher extends Member {

    @Builder
    public Researcher(String name, String major, String email, String image,
                      String dtype, String location, Login login) {
        super(name, major, email, image, dtype, location, login);
    }

    //
}
