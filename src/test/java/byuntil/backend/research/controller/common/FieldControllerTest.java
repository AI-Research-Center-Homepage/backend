package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.service.FieldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static byuntil.backend.common.factory.MockFieldFactory.createMockFields;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FieldControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    FieldService fieldService;

    @Test
    @DisplayName("연구분야 전체 조회 테스트")
    void readFields() throws Exception {
        List<Field> mockFields = createMockFields(2);

        when(fieldService.findAll()).thenReturn(mockFields);

        mockMvc.perform(get("/api/v1/field")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}