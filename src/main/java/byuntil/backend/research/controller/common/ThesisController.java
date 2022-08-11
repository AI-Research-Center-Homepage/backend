package byuntil.backend.research.controller.common;

import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.dto.response.thesis.ThesisResponseDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ThesisService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ThesisController {
    private final FieldService fieldService;
    private final ThesisService thesisService;

    @GetMapping("/thesis")
    public ResponseEntity readTheses() {
        return ResponseEntity.ok().body(thesisService.findAll());
    }

}
