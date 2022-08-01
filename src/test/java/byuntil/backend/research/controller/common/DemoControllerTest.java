package byuntil.backend.research.controller.common;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.service.DemoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static byuntil.backend.common.factory.MockResearchFactory.createMockDemos;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DemoService demoService;

    @Test
    @DisplayName("데모 전체 조회 테스트")
    void readDemos() throws Exception {
        List<Demo> mockDemos = createMockDemos(5);
        when(demoService.findAll()).thenReturn(mockDemos);

        mockMvc.perform(get("/api/v1/demo")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}