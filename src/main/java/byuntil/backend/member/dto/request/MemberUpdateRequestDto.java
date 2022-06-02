package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.Login;
import byuntil.backend.admin.domain.dto.LoginDto;
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
    private String number;

    private LoginDto loginDto;

    @Builder
    public MemberUpdateRequestDto(String name, String major, String email, String image, String position, String office,
                                  LocalDateTime admission, String doctorate, String number, String fields,
                                  LoginDto loginDto){
        this.name = name;
        this.major = major;
        this.email = email;
        this.image = image;
        this.position = position;
        this.admission = admission;
        this.doctorate = doctorate;
        this.number = number;
        this.office = office;
        this.fields = fields;
        this.loginDto = loginDto;
    }
}
