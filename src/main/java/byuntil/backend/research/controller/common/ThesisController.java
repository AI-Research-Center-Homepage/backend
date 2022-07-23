package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.dto.response.ThesisResponseDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ThesisService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
        List<Field> fields = fieldService.findAll();
        List<ThesisFieldDto> thesisFieldDtos = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = field.getName();

            List<ThesisDto> thesisDtos = thesisService.findAllByFieldName(fieldName);
            thesisFieldDtos.add(ThesisFieldDto.builder().fieldName(fieldName).theses(thesisDtos).build());
        }
        ThesisResponseDto<ThesisFieldDto> response = ThesisResponseDto.<ThesisFieldDto>builder().theses(thesisFieldDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @Getter
    @RequiredArgsConstructor
    @Builder
    static class ThesisFieldDto {
        private final String fieldName;
        private final List<ThesisDto> theses;
    }
}
