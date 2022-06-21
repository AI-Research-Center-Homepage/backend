package byuntil.backend.research;

import byuntil.backend.common.exception.ExistException;
import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ProjectRepository;
import byuntil.backend.research.domain.repository.ThesisRepository;
import byuntil.backend.research.dto.DemoDto;
import byuntil.backend.research.dto.FieldDto;
import byuntil.backend.research.dto.ProjectDto;
import byuntil.backend.research.service.DemoService;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@SpringBootTest
public class ResearchTest {
    @Autowired
    DemoService demoService;
    @Autowired
    ProjectService projectService;
    @Autowired
    DemoRepository demoRepository;
    @Autowired
    ThesisRepository thesisRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    FieldService fieldService;
    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    EntityManager em;

    public DemoDto makeDemoDto(){
        return DemoDto.builder().content("내s용d").name("데모1").url("url").fieldName("field1").build();
    }
    public ProjectDto makeProjectDto(){ return ProjectDto.builder().name("fie").fieldName("field1").content("내용123").participants("참여자들").build();}
    public FieldDto makeFieldDto(){
        return FieldDto.builder().name("field1").description("설명").build();
    }
    public FieldDto makeETCFieldDto(){ return FieldDto.builder().name("기타").description("설명1").build();}
    @Test
    @Commit
    @Transactional
    public void 연관관계테스트(){
        //given
        DemoDto demoDto = makeDemoDto();
        FieldDto fieldDto = makeFieldDto();
        //when
        fieldRepository.save(fieldDto.toEntity());
        Long demoId =demoService.save(demoDto);
        Demo demo = demoService.findById(demoId).get();
        //then
        org.assertj.core.api.Assertions.assertThat(demo.getContent()).isEqualTo(demoDto.getContent());
        Assertions.assertTrue(demo.getField().getName().equals(fieldDto.getName()));
    }

    @Test
    @Transactional
    @Commit
    public void demo의field모두불러오기(){
        //given
        DemoDto demoDto = makeDemoDto();
        FieldDto fieldDto = makeFieldDto();

        //when
        fieldRepository.save(fieldDto.toEntity());
        Long demoId =demoService.save(demoDto);
        Demo demo = demoService.findById(demoId).get();
        //then
        System.out.println("===============================");
        for (Optional<Field> f : fieldRepository.allDemoFields()) {
            System.out.println(f.get().getName() + " " + f.get().getDescription() + " "  + f.get().getDemoList());
        }
        System.out.println("===============================");

    }
    @Test
    @Transactional
    @Commit
    public void 삭제테스트(){
        //given
        FieldDto fieldDto = makeFieldDto();
        DemoDto demoDto = makeDemoDto();
        ProjectDto projectDto = makeProjectDto();
        //when
        fieldRepository.save(fieldDto.toEntity());
        demoService.save(demoDto);
        projectService.save(projectDto);
        Field field = fieldRepository.findByName(fieldDto.getName()).get();
        //post를 돌아가면서 해당 field에 속하면 모두 삭제하는.. sql을 작성해야할듯?


        thesisRepository.deleteByFieldName(field.getName());
        projectRepository.deleteByFieldName(field.getName());
        demoRepository.deleteByFieldName(field.getName());//이거 없으면 실행할 수 없다는 에러가 뜨게 됨
        fieldRepository.deleteByName(field.getName());

        em.flush();
        em.clear();//이거 없으면 영속성컨텍스트에서 가져오기때문에 테스트 실패

        //then
        org.assertj.core.api.Assertions.assertThat(fieldService.findById(field.getId())).isNotPresent();
    }
    @Test
    @Transactional
    public void 삭제시예외(){
        //given
        FieldDto fieldDto = makeFieldDto();
        DemoDto demoDto = makeDemoDto();
        //when
        fieldRepository.save(fieldDto.toEntity());
        Long demoId = demoService.save(demoDto);
        Demo demo = demoService.findById(demoId).get();
        Field field = fieldRepository.findByName(demoDto.getFieldName()).get();

        //then
        Assertions.assertThrows(ExistException.class, () -> {
            fieldService.deleteById(demo.getField().getId());
        });
    }
}
