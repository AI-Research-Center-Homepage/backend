package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Graduate;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GraduateSaveRequestDto extends MemberSaveRequestDto {

    private String admission;

    @Builder
    public GraduateSaveRequestDto(String name, String major, String email, String image, String admission, AdminDto adminDto) {
        super(name, major, email, image, adminDto);
        this.admission = admission;
    }


    @Override
    public Member toEntity() {
        return Graduate.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .admission(admission)
                .build();
    }
}
