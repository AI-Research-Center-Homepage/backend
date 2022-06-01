package byuntil.backend.member.dto.request;

import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.entity.member.Researcher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResearcherSaveRequestDto extends MemberSaveRequestDto {

    @Builder
    public ResearcherSaveRequestDto(String name, String major, String email, String image, String office, String fields) {
        super(name, major, email, image, office, fields);
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
