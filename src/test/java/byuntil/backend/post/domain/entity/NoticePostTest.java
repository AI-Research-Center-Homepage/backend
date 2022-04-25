package byuntil.backend.post.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static byuntil.backend.common.factory.MockPostFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class NoticePostTest {

    @DisplayName("공지사항 게시글에 첨부파일을 저장 할 수 있다.")
    @Test
    void addAttaches() {
        //given
        final NoticePost post = createMockNoticePost();
        final Attach attach = createMockAttach();
        attach.setNoticePost(post);
        //when
        post.addAttaches(attach);
        //then
        assertAll("첨부파일 저장 테스트",
                () -> assertThat(post.getAttaches().get(0).getServerFileName()).isEqualTo("orginal.txt1234"),
                () -> assertThat(post.getAttaches().get(0).getOriginFileName()).isEqualTo("orginal.txt"),
                () -> assertThat(post.getAttaches().get(0).getFilePath()).isEqualTo("/path")
        );
    }

    @DisplayName("공지사항 게시글에 첨부파일을 지울수있다")
    @Test
    void deleteAttaches() {
        //given
        final NoticePost post = createMockNoticePost();
        final Attach attach = createMockAttach();
        attach.setNoticePost(post);
        post.addAttaches(attach);
        //when
        post.deleteAttaches();
        //then
        assertAll("첨부파일 삭제 테스트",
                () -> assertThat(post.getAttaches()).isNullOrEmpty());
    }

    @Test
    void updatePost() {
        //given
        final NoticePost post = createMockNoticePost();
        //when
        String author = "author2";
        String title = "title2";
        String content = "noticePostContent";
        post.updatePost(createMockNoticePostDto(title, author, content));
        //then
        assertAll("notice 게시글 업데이트 테스트",
                () -> assertThat(post.getAuthor()).isEqualTo(author),
                () -> assertThat(post.getTitle()).isEqualTo(title),
                () -> assertThat(post.getContent()).isEqualTo(content));
    }
}