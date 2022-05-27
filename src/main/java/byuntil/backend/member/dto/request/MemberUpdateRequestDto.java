package byuntil.backend.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private String office;

    private String position;
    private String admission;
    private String doctorate;
    private String location;
    private String number;
    private String research;

    @Builder
    public MemberUpdateRequestDto(String name, String major, String email, String image, String position, String office,
                                  String admission, String doctorate, String location, String number, String research) {
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
        this.position = position;
        this.admission = admission;
        this.doctorate = doctorate;
        this.location = location;
        this.number = number;
        this.research = research;
        this.office = office;
    }
}
