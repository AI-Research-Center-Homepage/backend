package byuntil.backend.member.dto.request;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Researcher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResearcherSaveRequestDto extends MemberSaveRequestDto {

    @Builder
    public ResearcherSaveRequestDto(String name, String major, String email, String image, String office, String fields, AdminDto adminDto) {
        super(name, major, email, image, office, fields, adminDto);
    }


    @Override
    public Member toEntity() {
        return Researcher.builder()
                .name(getName())
                .major(getMajor())
                .email(getEmail())
                .image(getImage())
                .office(getOffice())
                .build();
    }
}
