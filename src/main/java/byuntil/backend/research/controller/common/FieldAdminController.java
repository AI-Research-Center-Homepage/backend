package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.dto.request.DemoDto;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/fields")
public class FieldAdminController {
    private final FieldService fieldService;

    @PostMapping()
    public ResponseEntity create(@RequestBody FieldDto fieldDto){
        fieldService.save(fieldDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PutMapping
    public ResponseEntity update(@RequestParam Long id, @RequestBody FieldDto fieldDto){
        fieldDto.setId(id);
        fieldService.update(fieldDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id){
        fieldService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    @GetMapping
    public ResponseEntity readAll(){
        List<FieldDto> fieldList = fieldService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(fieldList);
    }
}
