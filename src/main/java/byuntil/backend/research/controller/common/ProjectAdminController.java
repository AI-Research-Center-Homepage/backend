package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.dto.request.DemoDto;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ProjectDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/project/")
public class ProjectAdminController
{
    private final ProjectService projectService;
    private final FieldService fieldService;
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody ProjectDto projectDto){
        //TODO : 지워야하는 코드
        if(!fieldService.findByName("연구분야1").isPresent()) fieldService.save(FieldDto.builder().description("설명").name("연구분야1").build());


        projectService.save(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody ProjectDto projectDto){
        projectDto.setId(id);
        projectService.update(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id){
        projectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
    @GetMapping
    public ResponseEntity<?> readAll(){
        List<Project> projectList = projectService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@RequestParam Long id){
        Project project = projectService.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }
}
