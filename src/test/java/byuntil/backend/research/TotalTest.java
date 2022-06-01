package byuntil.backend.research;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.dto.DemoDto;
import byuntil.backend.research.dto.FieldDto;
import byuntil.backend.research.service.DemoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class TotalTest {
    @Autowired
    DemoService demoService;
    @Autowired
    DemoRepository demoRepository;
    @Autowired
    FieldRepository fieldRepository;
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
    public DemoDto makeDemoDto(){
        return DemoDto.builder().content("내s용").name("이s름").url("url").build();
    }
    public FieldDto makeFieldDto(){
        return FieldDto.builder().name("AI").description("설명").build();
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
            System.out.println(f.get().getName() + " " + f.get().getDescription() + " "  + f.get().getDemo().getName());
        }
        System.out.println("===============================");

    }
}
