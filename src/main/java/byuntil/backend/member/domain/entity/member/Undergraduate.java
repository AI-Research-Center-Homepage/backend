package byuntil.backend.member.domain.entity.member;

import byuntil.backend.member.domain.entity.Member_Thesis;
import byuntil.backend.s3.domain.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Undergraduate extends Member {
    private String admission;
    private String research;

    @Builder
    public Undergraduate(Long id, String name, String major, String email, String image, List<Member_Thesis> theses, List<Image> profile, List<Image> images, String admission, String research) {
        super(id, name, major, email, image, theses, profile, images);
        this.admission = admission;
        this.research = research;
    }
}
