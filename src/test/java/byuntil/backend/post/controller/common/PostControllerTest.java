package byuntil.backend.post.controller.common;


import byuntil.backend.post.domain.repository.BoardRepository;
import byuntil.backend.post.domain.repository.PostRepository;
import byuntil.backend.post.dto.PostDto;
import byuntil.backend.post.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    PostService postService;


    @Test
    @Transactional
    @DisplayName("게시물 수정 - autowired")
    void updatePostAutowired() throws IOException {
        //given
        PostDto originPostDto = PostDto.builder().title("제목1").content("내용1").author("저자").boardName("News").build();
        Long id = postService.save(originPostDto, null);
        PostDto newPostDto = PostDto.builder().title("새로운제목").content("새로운내용").author("새로운저자").boardName("News").build();
        //when
        postService.updatePost(id, newPostDto, null);
        //then
        PostDto dto1 = postService.findById(id).toDto();
        Assertions.assertThat(dto1.getTitle()).isEqualTo(newPostDto.getTitle());

    }

}
