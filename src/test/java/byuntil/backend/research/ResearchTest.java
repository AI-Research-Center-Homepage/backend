package byuntil.backend.research;

import byuntil.backend.common.exception.ExistException;
import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Project;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.domain.repository.FieldRepository;
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
    FieldService fieldService;
    @Autowired
    FieldRepository fieldRepository;

    public DemoDto makeDemoDto(){
        return DemoDto.builder().content("내s용d").name("데모1").url("url").build();
    }
    public ProjectDto makeProjectDto(){ return ProjectDto.builder().name("프로젝트1").content("내용123").participants("참여자들").build();}
    public FieldDto makeFieldDto(){
        return FieldDto.builder().name("필드2sss").description("설명").build();
    }
    public FieldDto makeETCFieldDto(){ return FieldDto.builder().name("기타").description("설명1").build();}
    @Test
    @Commit
    @Transactional
    public void 연관관계테스트(){
        //given
        DemoDto demoDto = makeDemoDto();
        FieldDto fieldDto = makeFieldDto();
        demoDto.setFieldDto(fieldDto);
        //when
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
        demoDto.setFieldDto(fieldDto);
        //when
        Long demoId =demoService.save(demoDto);
        Demo demo = demoService.findById(demoId).get();
        //then
        System.out.println("===============================");
        for (Optional<Field> f : fieldRepository.allDemoFields()) {
            System.out.println(f.get().getName() + " " + f.get().getDescription() + " "  + f.get().getDemo());
        }
        System.out.println("===============================");

    }
    @Test
    @Transactional
    @Commit
    public void 삭제테스트(){
        //given
        FieldDto fieldDto = makeFieldDto();
        //when
        Long id = fieldService.save(fieldDto);
        fieldService.deleteById(id);
        //then
        org.assertj.core.api.Assertions.assertThat(fieldService.findById(id)).isNotPresent();
    }
    @Test
    @Transactional
    public void 삭제시예외(){
        //given
        FieldDto fieldDto = makeFieldDto();
        DemoDto demoDto = makeDemoDto();
        demoDto.setFieldDto(fieldDto);
        //when
        Long demoId = demoService.save(demoDto);
        Demo demo = demoService.findById(demoId).get();

        //then
        Assertions.assertThrows(ExistException.class, () -> {
            fieldService.deleteById(demo.getField().getId());
        });
    }
}
