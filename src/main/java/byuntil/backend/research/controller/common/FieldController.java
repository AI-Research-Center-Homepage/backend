package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.dto.response.FieldResponseDto;
import byuntil.backend.research.service.FieldService;
import lombok.Getter;
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
    public ResponseEntity<?> readFields() {
        List<Field> fields = fieldService.findAll();
        List<FieldDto> fieldDtos = fields.stream().
                map(FieldDto::new)
                .toList();
        FieldResponseDto<FieldDto> response = FieldResponseDto.<FieldDto>builder().fields(fieldDtos).build();
        return ResponseEntity.ok().body(response);
    }

    @Getter
    static class FieldDto {
        private final String fieldName;
        private final String description;

        public FieldDto(final Field field) {
            this.fieldName = field.getName();
            this.description = field.getDescription();
        }
    }
}
