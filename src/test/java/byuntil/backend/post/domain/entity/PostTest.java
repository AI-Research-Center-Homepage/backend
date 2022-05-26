package byuntil.backend.post.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static byuntil.backend.common.factory.MockPostFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostTest {

    @DisplayName("뉴스 게시글에 첨부파일을 저장 할 수 있다.")
    @Test
    void addAttaches() {
        //given
        final Post post = createMockPost();
        final Attach attach = createMockAttach();
        attach.addPost(post);
        //when
        post.addAttach(attach);

        //then
        assertAll("첨부파일 저장 테스트",
                () -> assertThat(post.getAttaches().get(0).getServerFileName()).isEqualTo("orginal.txt1234"),
                () -> assertThat(post.getAttaches().get(0).getOriginFileName()).isEqualTo("orginal.txt"),
                () -> assertThat(post.getAttaches().get(0).getFilePath()).isEqualTo("/path")
        );
    }

    @DisplayName("뉴스 게시글에 첨부파일을 지울수있다.")
    @Test
    void deleteAttaches() {
        //given
        final Post post = createMockPost();

        final Attach attach = createMockAttach();
        attach.addPost(post);
        post.addAttach(attach);
        //when
        post.deleteAttaches();

        //then
        assertAll("첨부파일 삭제 테스트",
                () -> assertThat(post.getAttaches()).isNullOrEmpty());
    }

    @Test
    void updatePost() {
        //given
        final Post post = createMockPost();
        //when
        String author = "author2";
        String title = "title2";
        String content = "newsPostContent";
        post.updatePost(createMockPostDto(title, author, content));
        //then
        assertAll("news 게시글 업데이트 테스트",
                () -> assertThat(post.getTitle()).isEqualTo(title),
                () -> assertThat(post.getContent()).isEqualTo(content));
    }
}