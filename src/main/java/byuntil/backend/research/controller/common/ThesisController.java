package byuntil.backend.research.controller.common;

import byuntil.backend.research.service.ThesisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ThesisController {
    private final ThesisService thesisService;

    @GetMapping("/thesis")
    public ResponseEntity readTheses() {
        return ResponseEntity.ok().body(thesisService.findAll());
    }

}
