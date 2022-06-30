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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FieldController {
    private final FieldService fieldService;

    @GetMapping("/fields")
    public ResponseEntity<?> readFields() {
        List<Field> fields = fieldService.findAll();
        List<FieldDto> dtos = fields.stream().
                map(o -> new FieldDto(o))
                .collect(Collectors.toList());
        FieldResponseDto<FieldDto> response = FieldResponseDto.<FieldDto>builder().fields(dtos).build();
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
