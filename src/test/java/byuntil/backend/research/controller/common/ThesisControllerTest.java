package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.dto.request.FieldDto;
import byuntil.backend.research.dto.request.ThesisDto;
import byuntil.backend.research.service.FieldService;
import byuntil.backend.research.service.ThesisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static byuntil.backend.common.factory.MockFieldFactory.createMockFieldDtos;
import static byuntil.backend.common.factory.MockFieldFactory.createMockFields;
import static byuntil.backend.common.factory.MockResearchFactory.createMockTheses;
import static byuntil.backend.common.factory.MockResearchFactory.createMockThesisDtos;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ThesisControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ThesisService thesisService;

    @Autowired
    FieldService fieldService;

    @Test
    @Transactional
    @DisplayName("논문 전체 조회 테스트")
    void readTheses() throws Exception {
        int FIELD_NUM = 2;
        int THESIS_NUM = 5;

        //Thesis, Field;
        List<Thesis> mockTheses = createMockTheses(FIELD_NUM, THESIS_NUM);
        List<Field> mockFields = createMockFields(FIELD_NUM);

        //DTO 변환
        List<ThesisDto> mockThesisDtos = createMockThesisDtos(mockTheses);
        List<FieldDto> mockFieldDtos = createMockFieldDtos(mockFields);

        //save
        mockFieldDtos.stream().map(FieldDto -> fieldService.save(FieldDto)).toList();
        mockThesisDtos.stream().map(ThesisDto -> thesisService.save(ThesisDto)).toList();

        mockMvc.perform(get("/api/v1/thesis")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}