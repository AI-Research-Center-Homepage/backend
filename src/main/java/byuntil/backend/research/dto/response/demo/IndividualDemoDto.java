package byuntil.backend.research.dto.response.demo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter//이 어노테이션을 추가하지 않으면  serializer를 찾을수 없는 오류가 뜨게 된다
public class IndividualDemoDto {
    private String name;
    private String description;
    private String content;
    private String url;
    private String participants;
}
