package byuntil.backend.member.dto.request;

import byuntil.backend.admin.controlller.domain.dto.LoginDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Undergraduate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UndergraduateSaveRequestDto extends MemberSaveRequestDto {

    private LocalDateTime admission;
    private String research;

    @Builder
    public UndergraduateSaveRequestDto(String name, String major, String email, String image,
                                       LocalDateTime admission, String research, String office,
                                       String fields, LoginDto loginDto) {
        super(name, major, email, image, office, fields, loginDto);
        this.admission = admission;
        this.research = research;
    }

    @Override
    public Member toEntity() {
        return Undergraduate.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .admission(admission)
                .login(getLoginDto().toEntity())
                .office(getOffice())
                .build();
    }
}
