package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Category;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.dto.FieldDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FieldServiceTest {
    @Autowired
    private FieldService fieldService;

    @Test
    public void 논문분야저장(){
        //given
        FieldDto fieldDto = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        //when
        Long fieldId = fieldService.save(fieldDto);
        Field field = fieldService.findById(fieldId).get();
        //then
        Assertions.assertThat(field.getDescription()).isEqualTo("연구분야:ai");
    }
    @Test
    public void 논문분야업데이트(){
        //given
        FieldDto origin = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        FieldDto update = FieldDto.builder().category(Category.AR_AI).description("수정후").build();
        //when
        Long id = fieldService.save(origin);
        fieldService.update(id, update);
        Field updateOne = fieldService.findById(id).get();
        //then
        Assertions.assertThat(updateOne.getDescription()).isEqualTo("수정후");
    }
    @Test
    public void 논문분야삭제(){
        //given
        FieldDto fieldDto = FieldDto.builder().category(Category.AR_AI).description("연구분야:ai").build();
        //when
        Long fieldId = fieldService.save(fieldDto);
        fieldService.delete(fieldId);
        Optional<Field> field = fieldService.findById(fieldId);
        //then
        Assertions.assertThat(field).isNotPresent();;
    }
    @Test
    public void 논문분야전체조회(){
        //given
        FieldDto field1 = FieldDto.builder().category(Category.AR_AI).description("설명1").build();
        FieldDto field2 = FieldDto.builder().category(Category.AR_AI).description("설명2").build();
        //when
        fieldService.save(field1);
        fieldService.save(field2);
        int size = fieldService.findAll().size();
        //then
        Assertions.assertThat(size).isEqualTo(2);
    }


}