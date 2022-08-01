package byuntil.backend.post.controller.common;

import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.service.BoardService;
import byuntil.backend.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static byuntil.backend.common.factory.MockBoardFactory.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @MockBean
    PostService postService;

    @Test
    @DisplayName("뉴스 미리보기 조회 테스트")
    void readPreviewNews() throws Exception {
        //given
        Board news = createMockBoard("News", 2, false);
        when(boardService.findByName("News")).thenReturn(news);

        mockMvc.perform(get("/api/v1/news")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("뉴스 자세히 보기 조회 테스트")
    void readEachNews() throws Exception {
        Long postId = 1L;
        Post hi = createMockPost(postId, "hi");
        when(postService.findById(postId)).thenReturn(hi);

        mockMvc.perform(get("/api/v1/news/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("공지사항 자세히 보기 조회 테스트 - 첨부 X")
    void readEachNoticeNoAttach() throws Exception {
        Long postId = 1L;
        Post hi = createMockPost(postId, "hi");
        when(postService.findById(postId)).thenReturn(hi);

        mockMvc.perform(get("/api/v1/notice/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("공지사항 자세히 보기 조회 테스트 - 첨부 O")
    void readEachNoticeYesAttach() throws Exception {
        Long postId = 1L;
        Post hi = createMockPostIncludeAttach(postId, "hi", 3);
        when(postService.findById(postId)).thenReturn(hi);

        mockMvc.perform(get("/api/v1/notice/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}