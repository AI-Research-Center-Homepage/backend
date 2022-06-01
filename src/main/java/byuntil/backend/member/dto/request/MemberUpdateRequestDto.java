package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.dto.AdminDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private String office;
    private String fields;

    private String position;
    private LocalDateTime admission;
    private String doctorate;
    private String location;
    private String number;
    private String research;

    private AdminDto adminDto;

    @Builder
    public MemberUpdateRequestDto(String name, String major, String email, String image, String position, String office,
                                  LocalDateTime admission, String doctorate){
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
        this.position = position;
        this.admission = admission;
        this.doctorate = doctorate;
        this.location = location;
        this.number = number;
        this.office = office;
        this.fields = fields;
        this.adminDto = adminDto;
    }
}
