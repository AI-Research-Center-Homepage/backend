package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Committee;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommitteeSaveRequestDto extends MemberSaveRequestDto {
    private String position;

    @Builder
    public CommitteeSaveRequestDto(String name, String major, String email, String image, String position, AdminDto adminDto) {
        super(name, major, email, image, adminDto);
        this.position = position;
    }


    @Override
    public Member toEntity() {
        return Committee.builder()
                .name(getName())
                .email(getEmail())
                .image(getImage())
                .major(getMajor())
                .position(getPosition())
                .admin(getAdminDto().toEntity())
                .build();
    }
}
