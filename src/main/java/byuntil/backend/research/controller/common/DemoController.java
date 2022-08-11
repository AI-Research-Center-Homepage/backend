package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.service.DemoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DemoController {
    private final DemoService demoService;

    @GetMapping("/demo")
    public ResponseEntity readDemos() {
        return ResponseEntity.ok().body(demoService.findAll());
    }

    @Getter
    static class DemoDto {
        private final Long id;
        private final String title;
        private final String description;
        private final String content;
        private final String url;
        private final String participants;

        public DemoDto(final Demo demo) {
            this.id = demo.getId();
            this.title = demo.getTitle();
            this.description = demo.getDescription();
            this.content = demo.getContent();
            this.url = demo.getUrl();
            this.participants = demo.getParticipants();
        }
    }
}
