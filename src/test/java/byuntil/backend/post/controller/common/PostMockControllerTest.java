package byuntil.backend.post.controller.common;

import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.domain.repository.PostRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.service.BoardService;
import byuntil.backend.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static byuntil.backend.common.factory.MockBoardFactory.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostMockControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @MockBean
    PostService postService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 조회")
    void readPost() throws Exception {
        //given

        Long postId = 1L;
        Post hi = createMockPost(postId, "hi");
        Board board = createMockBoard("News",2,false);
        hi.setBoard(board);

        //when
        when(postService.findById(postId)).thenReturn(hi);
        //then
        mockMvc.perform(get("/api/v1/admin/posts/")
                        .param("id", String.valueOf(postId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("게시글 수정 - mock")
    void updatePostMock() throws IOException {
        //given
        PostDto originPostDto = PostDto.builder().title("제목1").id(1l).content("내용1").author("저자").boardName("News").build();
        when(postService.save(originPostDto, null))
                .thenReturn(1l);
        PostDto newPostDto = PostDto.builder().title("새로운제목").id(1l).content("새로운내용").author("새로운저자").boardName("News").build();
        //when
        postService.updatePost(1l, newPostDto, null);
        //then
        when(postService.findById(1l)).thenReturn(createMockPost(1l, "새로운제목"));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        //given
        PostDto postDto = PostDto.builder().title("제목1").id(1l).content("내용1").author("저자").boardName("News").build();
        //when
        when(postService.save(postDto, null))
                .thenReturn(1l);
        //then
        mockMvc.perform(delete("/api/v1/admin/posts/")
                        .param("id", String.valueOf(1l)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 등록")
    void createPosts() throws Exception {
        //postService가 MockBean으로 등록되어있는 점 주의해야함
        //given
        PostDto postDto =  PostDto.builder().boardName("News").image("image1").content("내용1").title("제목").id(1l).build();
        String content = objectMapper.writeValueAsString(postDto);
        MockPart part = new MockPart("postDto", content.getBytes(StandardCharsets.UTF_8));
        part.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //when
        mockMvc.perform(multipart("/api/v1/admin/posts/")
                        .part(part))
                .andExpect(status().isCreated())
                .andDo(print());
        //then
    }

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