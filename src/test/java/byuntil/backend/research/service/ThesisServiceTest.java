package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Category;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.domain.repository.ThesisRepository;
import byuntil.backend.research.dto.FieldDto;
import byuntil.backend.research.dto.ThesisDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class ThesisServiceTest {
    @Autowired
    private ThesisService thesisService;

    @Test
    public void 논문저장(){
        //given
        ThesisDto thesisDto = ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        Long id =thesisService.save(thesisDto);
        Thesis thesis = thesisService.findById(id).get();

        //then
        Assertions.assertThat(thesisDto.getEnName()).isEqualTo(thesis.getEnName());
    }
    @Test
    public void 논문업데이트(){
        //given
        ThesisDto originOne = ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        ThesisDto updateOne = ThesisDto.builder().enName("minji").koName("수정후").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        Long id =thesisService.save(originOne);
        thesisService.update(id, updateOne);
        Thesis thesis = thesisService.findById(id).get();

        //then
        Assertions.assertThat(thesis.getKoName()).isEqualTo("수정후");

    }
    @Test
    public void 논문삭제(){
        //given
        ThesisDto thesisDto = ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        Long id =thesisService.save(thesisDto);
        thesisService.delete(id);
        Optional<Thesis> thesis = thesisService.findById(id);

        //then
        Assertions.assertThat(thesis).isNotPresent();;
    }
    @Test
    public void 논문전체조회(){
        //given
        ThesisDto thesis1 = ThesisDto.builder().enName("minji").koName("민지1").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        ThesisDto thesis2 = ThesisDto.builder().enName("minji").koName("민지2").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        thesisService.save(thesis1);
        thesisService.save(thesis2);
        int size = thesisService.findAll().size();
        //then
        Assertions.assertThat(size).isEqualTo(2);

    }
    /*
    @Test
    public void save(){
        //given
        FieldDto fieldDto = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        ThesisDto thesisDto = ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        Long id =thesisService.save(thesisDto);
        Thesis thesis = thesisService.findById(id).get();

        Long fieldId = fieldService.save(fieldDto);
        Field field = fieldService.findById(fieldId).get();
        //then
        Assertions.assertThat(thesisDto.getEnName()).isEqualTo(thesis.getEnName());
        Assertions.assertThat(fieldDto.getDescription()).isEqualTo(thesis.getField().getDescription());
    }
    @Test
    public void update(){
        //given
        FieldDto originField = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        ThesisDto originThesis = ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();

        FieldDto updateField = FieldDto.builder().category(Category.AR_AI).description("수정됨").build();
        ThesisDto updateThesis = ThesisDto.builder().enName("minji").koName("변경된이름").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        Long fieldId = fieldService.save(originField);
        Long thesisId =thesisService.save(originThesis);
        Thesis thesis = thesisService.findById(thesisId).get();
        Field field = fieldService.findById(fieldId).get();
        thesis.setField(field);

        fieldService.update(fieldId, updateField);
        thesisService.update(thesisId, updateThesis);

        Thesis updateOne = thesisService.findById(thesisId).get();
        //then
        Assertions.assertThat(updateOne.getEnName()).isEqualTo(updateThesis.getEnName());
        Assertions.assertThat(updateOne.getField().getDescription()).isEqualTo("수정됨");

    }
    @Test
    public void delete(){
        //given
        FieldDto fieldDto = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        ThesisDto thesisDto = ThesisDto.builder().enName("minji").koName("민지").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).build();
        //when
        Long id =thesisService.save(thesisDto);
        thesisService.delete(id);
        Optional<Thesis> findOne = thesisService.findById(id);

        Long fieldId = fieldService.save(fieldDto);
        Field field = fieldService.findById(fieldId).get();
        findOne.get().setField(field);
        //then
        Assertions.assertThat(findOne).isNotPresent();
    }
    @Test
    public void 연관관계설정된값findall(){
        //given
        FieldDto fieldDto1 = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        ThesisDto thesisDto1 = ThesisDto.builder().enName("minji").koName("민지1").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).fieldDto(fieldDto1).build();

        FieldDto fieldDto2 = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        ThesisDto thesisDto2 = ThesisDto.builder().enName("minji").koName("민지2").url("https://localhost:8080").journal("dd")
                .title("제목1").publishDate(LocalDateTime.now()).fieldDto(fieldDto2).build();

        //when

        fieldService.save(fieldDto1);
        fieldService.save(fieldDto2);

        Long thesisId1 = thesisService.save(thesisDto1);
        Long thesisId2 = thesisService.save(thesisDto2);

        Thesis thesis1 = thesisService.findById(thesisId1).get();
        Thesis thesis2 = thesisService.findById(thesisId2).get();



        int thesisSize = thesisService.findAll().size();
        int fieldSize = fieldService.findAll().size();
        //then
        Assertions.assertThat(thesisSize).isEqualTo(2);
        Assertions.assertThat(fieldSize).isEqualTo(2);

    }*/

}