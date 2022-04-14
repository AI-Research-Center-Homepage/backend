package byuntil.backend.entity.member;

import byuntil.backend.entity.Image;
import byuntil.backend.entity.Member_Thesis;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends Member {
    private String doctorate;
    private String location;
    private String number;

    @Builder
    public Professor(Long id, String name, String major, String email, String image, List<Member_Thesis> theses, List<Image> profile, List<Image> images, String doctorate, String location, String number) {
        super(id, name, major, email, image, theses, profile, images);
        this.doctorate = doctorate;
        this.location = location;
        this.number = number;
    }
}
