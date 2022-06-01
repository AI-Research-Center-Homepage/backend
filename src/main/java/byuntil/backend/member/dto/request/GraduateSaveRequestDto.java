package byuntil.backend.member.dto.request;

import byuntil.backend.member.domain.entity.member.Graduate;
import byuntil.backend.member.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GraduateSaveRequestDto extends MemberSaveRequestDto {

    private LocalDateTime admission;

    @Builder
    public GraduateSaveRequestDto(String name, String major, String email, String image,
                                  LocalDateTime admission, String office, String fields) {
        super(name, major, email, image, office, fields);
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
                .office(getOffice())
                .build();
    }
}
