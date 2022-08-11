package byuntil.backend.research.controller.common;

import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.response.field.FieldResponseDto;
import byuntil.backend.research.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FieldController {
    private final FieldService fieldService;

    @GetMapping("/fields")
    public ResponseEntity readFields() {
        List<FieldDto> fields = fieldService.findAll();
        FieldResponseDto<FieldDto> response = FieldResponseDto.<FieldDto>builder().fields(fields).build();
        return ResponseEntity.ok().body(response);
    }

}
