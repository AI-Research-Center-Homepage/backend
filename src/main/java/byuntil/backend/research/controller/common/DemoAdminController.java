package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.dto.request.DemoDto;
import byuntil.backend.research.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/demo/")
public class DemoAdminController {
    private final DemoService demoService;
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody DemoDto demoDto){
        demoService.save(demoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody DemoDto demoDto){
        demoDto.setId(id);
        demoService.update(demoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id){
        demoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    @GetMapping
    public ResponseEntity<?> readAll(){
        List<Demo> demoList = demoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(demoList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@RequestParam Long id){
        Demo demo = demoService.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(demo);
    }

}
