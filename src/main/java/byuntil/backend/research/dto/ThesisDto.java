package byuntil.backend.research.dto;

import byuntil.backend.member.dto.request.MemberSaveRequestDto;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ThesisDto {
    private Long id;
    private String title;
    private String koName;
    private String enName;
    private String journal;
    private LocalDateTime publishDate;
    private String url;

    //연관관게는 entity로 바꾼 뒤에 넣어주는 걸로..
    //private FieldDto fieldDto;

    public Thesis toEntity(){
        Thesis thesis = Thesis.builder().enName(this.enName).koName(this.koName)
                .id(id)
                .title(this.title).journal(this.journal).publishDate(this.publishDate)
                .url(this.url).build();
        return thesis;
    }
}
