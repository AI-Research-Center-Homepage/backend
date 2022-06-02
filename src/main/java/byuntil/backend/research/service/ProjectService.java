package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ProjectRepository;
import byuntil.backend.research.dto.ProjectDto;
import byuntil.backend.research.dto.ThesisDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final FieldRepository fieldRepository;
    @Transactional
    public Long save(ProjectDto projectDto){
        Project project =projectDto.toEntity();
        Field field = projectDto.getFieldDto().toEntity();
        field.addProject(project);
        fieldRepository.save(field);
        return projectRepository.save(project).getId();
    }
    @Transactional
    public void update(ProjectDto projectDto){
        Project project = projectRepository.findById(projectDto.getId()).orElseThrow(()
                ->new IllegalArgumentException("해당 프로젝트가 없습니다. id = "+projectDto.getId()));
        project.update(projectDto);
        if(!project.getField().getId().equals(projectDto.getFieldDto().getId())){
            //내용만 변경되는건 안됨 field는 field혼자만 변경되어야함
            Field field = fieldRepository.findById(projectDto.getFieldDto().getId()).get();
            field.addProject(project);
        }
    }
    @Transactional
    public void delete(Long id){
        projectRepository.deleteById(id);
    }
    public Optional<Project> findById(Long id){
        return projectRepository.findById(id);
    }
    public List<Project> findAll(){
        return projectRepository.findAll();
    }
}
