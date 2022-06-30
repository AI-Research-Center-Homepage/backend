package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Thesis;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class ThesisDto {
    private final Long id;
    private final String title;
    private final String koName;
    private final String enName;
    private final String journal;
    private final LocalDateTime publishDate;
    private final String url;
    private final String fieldName;

    public ThesisDto(final Thesis thesis) {
        this.id = thesis.getId();
        this.title = thesis.getTitle();
        this.koName = thesis.getKoName();
        this.enName = thesis.getEnName();
        this.journal = thesis.getJournal();
        this.publishDate = thesis.getPublishDate();
        this.url = thesis.getUrl();
        this.fieldName = thesis.getField().getName();
    }

    public Thesis toEntity() {
        return Thesis.builder().enName(this.enName).koName(this.koName)
                .title(this.title).journal(this.journal).publishDate(this.publishDate)
                .url(this.url).build();
    }
}
