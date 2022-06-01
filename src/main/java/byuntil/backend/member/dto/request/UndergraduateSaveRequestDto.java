package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Undergraduate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UndergraduateSaveRequestDto extends MemberSaveRequestDto {

    private String admission;
    private String research;

    @Builder
    public UndergraduateSaveRequestDto(String name, String major, String email, String image, String admission, String research, AdminDto adminDto) {
        super(name, major, email, image, adminDto);
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
                .research(research)
                .build();
    }
}
