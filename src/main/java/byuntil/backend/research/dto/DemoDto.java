package byuntil.backend.research.dto;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoDto {
    private Long id;
    private String name;
    private String content;
    private String url;
    private String participants;
    private String fieldName;
    public Demo toEntity(){
        return Demo.builder().name(name).content(content).url(url).participants(participants).build();
    }
}
