package byuntil.backend.common.factory;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.ProjectDto;
import byuntil.backend.research.dto.ThesisDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static byuntil.backend.common.factory.MockFieldFactory.createMockField;

public class MockResearchFactory {

    //Project
    public static Project createMockProject(String title, Field field, String description, String content, String participants) {
        Project project = Project.builder()
                .title(title)
                .content(content)
                .description(description)
                .participants(participants)
                .build();
        project.setField(field);
        return project;
    }

    public static List<Project> createMockProjects(int fieldNum, int projectNum) {
        List<Project> projects = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        for (int j = 0; j < fieldNum; j++) {
            fields.add(createMockField(j));
        }

        //ex) fieldNum = 2 , projectNum = 5 : => 연구분야0 : (프로젝트)제목2, 제목4 | 연구분야1: (프로젝트)제목1, 제목3, 제목5
        for (int i = 1; i <= projectNum; i++) {
            projects.add(createMockProject("제목" + i, fields.get(i % fieldNum), "요약" + i, "내용" + i, "참가자"));
        }
        return projects;
    }

    public static List<ProjectDto> createMockProjectDtos(List<Project> projects) {
        return projects.stream().map(ProjectDto::new).toList();
    }

    //Theis
    public static Thesis createMockThesis(Field field, ThesisVO thesisVO) {
        Thesis thesis = Thesis.builder()
                .title(thesisVO.title)
                .koName(thesisVO.koName)
                .enName(thesisVO.enName)
                .journal(thesisVO.journal)
                .publishDate(thesisVO.publishDate)
                .url(thesisVO.url)
                .build();
        thesis.setField(field);
        return thesis;
    }

    public static List<Thesis> createMockTheses(int fieldNum, int thesisNum) {
        List<Thesis> theses = new ArrayList<>();
        List<Field> fields = new ArrayList<>();

        for (int j = 0; j < fieldNum; j++) {
            fields.add(createMockField(j));
        }

        for (int i = 1; i <= thesisNum; i++) {
            ThesisVO thesisVO = ThesisVO.builder()
                    .id((long) (i))
                    .title("제목" + i)
                    .koName("한국이름" + i)
                    .enName("영어이름" + i)
                    .journal("저널" + i)
                    .publishDate(LocalDateTime.now())
                    .url("url" + i).build();
            theses.add(createMockThesis(fields.get(i % fieldNum), thesisVO));
        }
        return theses;
    }

    public static List<ThesisDto> createMockThesisDtos(List<Thesis> theses) {
        return theses.stream().map(ThesisDto::new).toList();
    }

    @Getter
    @Builder
    private static class ThesisVO {
        private Long id;
        private String title;
        private String koName;
        private String enName;
        private String journal;
        private LocalDateTime publishDate;
        private String url;

    }
}
