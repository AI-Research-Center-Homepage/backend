package byuntil.backend.research.service;

import byuntil.backend.common.exception.research.NullFieldException;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ProjectRepository;
import byuntil.backend.research.dto.request.ProjectDto;
import byuntil.backend.research.dto.response.project.AllProjectResponseDto;
import byuntil.backend.research.dto.response.project.IndividualProjectDto;
import byuntil.backend.research.dto.response.project.OneProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final FieldRepository fieldRepository;

    @Transactional
    public Long save(final ProjectDto projectDto) {
        Project project = projectDto.toEntity();
        Field field = fieldRepository.findByName(projectDto.getFieldName()).orElseThrow(() -> new NullFieldException());
        field.addProject(project);
        return projectRepository.save(project).getId();
    }

    @Transactional
    public void update(final ProjectDto projectDto) {
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
    public void delete(final Long id) {
        projectRepository.deleteById(id);
    }

    public IndividualProjectDto findById(final Long id) {
        ProjectDto dto = projectRepository.findById(id).get().toDto();

        return IndividualProjectDto.builder().content(dto.getContent()).name(dto.getName()).description(dto.getDescription())
                .fieldName(dto.getFieldName()).participants(dto.getParticipants()).build();
    }

    public List<AllProjectResponseDto> findAll() {
        List<String> fieldNames = new ArrayList<>();
        List<AllProjectResponseDto> projectDtoList = new ArrayList<>();
        for (Field field: fieldRepository.findAll()) {
            fieldNames.add(field.getName());
        }
        for (String fieldName: fieldNames) {
            List<ProjectDto> projectDtoListByName = findAllByFieldName(fieldName);
            List<OneProjectDto> oneProjectDtoList = new ArrayList<>();
            for (ProjectDto projectDto : projectDtoListByName) {
                OneProjectDto one = OneProjectDto.builder().description(projectDto.getDescription()).title(projectDto.getName())
                                .id(projectDto.getId()).build();
                oneProjectDtoList.add(one);
            }
            projectDtoList.add(AllProjectResponseDto.builder().fieldName(fieldName).projects(oneProjectDtoList).build());
        }

        return projectDtoList;
    }

    public List<ProjectDto> findAllByFieldName(final String name) {
        List<Project> projectList = projectRepository.findAllByFieldName(name);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project: projectList) {
            projectDtoList.add(project.toDto());
        }
        return projectDtoList;
    }
}
