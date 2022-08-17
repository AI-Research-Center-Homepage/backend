package byuntil.backend.research.controller.common;

import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.response.field.FieldResponseDto;
import byuntil.backend.research.dto.response.field.GeneralOneFieldDto;
import byuntil.backend.research.service.FieldService;
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
public class FieldController {
    private final FieldService fieldService;

    @GetMapping("/fields")
    public ResponseEntity readFields() {
        return ResponseEntity.ok().body(fieldService.findAll());
    }


}
