package byuntil.backend.research.controller.common;

import byuntil.backend.research.dto.request.DemoDto;
import byuntil.backend.research.dto.response.demo.AllDemoResponseDto;
import byuntil.backend.research.dto.response.demo.DemoListResponseDto;
import byuntil.backend.research.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/demo")
public class DemoAdminController {
    private final DemoService demoService;
    @PostMapping("/new")
    public ResponseEntity create(@RequestBody DemoDto demoDto){
        demoService.save(demoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @PutMapping
    public ResponseEntity update(@RequestParam("demoId")Long id, @RequestBody DemoDto demoDto){
        demoDto.setId(id);
        demoService.update(demoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("demoId") Long id){
        demoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    @GetMapping
    public ResponseEntity readAll(){
        List<AllDemoResponseDto> demoList = demoService.findAllWithAdmin();
        return ResponseEntity.status(HttpStatus.OK).body(DemoListResponseDto.builder().demos(demoList).build());
    }
    @GetMapping("/{demoId}")
    public ResponseEntity readById(@PathVariable("demoId") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(demoService.findById(id));
    }

}
