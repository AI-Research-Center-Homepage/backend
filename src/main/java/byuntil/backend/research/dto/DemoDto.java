package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Demo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class DemoDto {
    private final Long id;
    private final String name;
    private final String content;
    private final String url;
    private final String participants;
    private final String fieldName;

    public DemoDto(final Demo demo) {
        this.id = demo.getId();
        this.name = demo.getName();
        this.content = demo.getContent();
        this.url = demo.getUrl();
        this.participants = demo.getParticipants();
        this.fieldName = demo.getField().getName();
    }

    public Demo toEntity() {
        return Demo.builder().name(name).content(content).url(url).participants(participants).build();
    }
}
