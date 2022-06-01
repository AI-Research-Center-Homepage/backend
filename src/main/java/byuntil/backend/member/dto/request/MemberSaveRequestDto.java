package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class MemberSaveRequestDto {
    private String name;
    private String major;
    private String email;
    private String image;
    private AdminDto adminDto;

    abstract public Member toEntity();

    public Admin dtosToEntity() {
        Member member = this.toEntity();
        Admin admin = this.getAdminDto().toEntity();
        admin.addMember(member);
        return admin;
    }
}
