package byuntil.backend.research.service;

import byuntil.backend.common.exception.research.NullFieldException;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ProjectRepository;
import byuntil.backend.research.dto.ProjectDto;
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
    public Long save(ProjectDto projectDto) {
        Project project = projectDto.toEntity();
        Field field = fieldRepository.findByName(projectDto.getFieldName()).orElseThrow(() -> new NullFieldException());
        field.addProject(project);
        return projectRepository.save(project).getId();
    }

    @Transactional
    public void update(ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.getId()).orElseThrow(()
                -> new IllegalArgumentException("해당 프로젝트가 없습니다. id = " + projectDto.getId()));
        project.update(projectDto);
        if (!project.getField().getName().equals(projectDto.getFieldName())) {
            //내용만 변경되는건 안됨 field는 field혼자만 변경되어야함
            Field field = fieldRepository.findByName(projectDto.getFieldName()).get();
            field.addProject(project);
        }
    }

    @Transactional
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findAllByFieldName(String name) {
        return projectRepository.findAllByFieldName(name);
    }
}
