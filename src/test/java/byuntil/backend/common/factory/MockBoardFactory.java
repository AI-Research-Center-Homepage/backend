package byuntil.backend.common.factory;

import byuntil.backend.post.domain.entity.Attach;
import byuntil.backend.post.domain.entity.Board;
import byuntil.backend.post.domain.entity.Post;
import byuntil.backend.post.dto.PostDto;

import java.util.ArrayList;
import java.util.List;

public class MockBoardFactory {
    public static Post createMockPostIncludeAttach(Long postId, String postName, int attachNum) {
        Post post = Post.builder().id(postId).title(postName).content("내용").viewNum(1).image("이미지").build();
        for (int i = 0; i < attachNum; i++) {
            post.addAttach(Attach.builder().filePath("/파일경로" + i).originFileName("파일이름" + i).build());
        }

        return post;
    }

    public static Post createMockPost(Long postId, String postName) {
        return Post.builder().id(postId).title(postName).content("내용").viewNum(1).
                image("이미지").build();
    }

    public static Board createMockBoard(String boardName, int postNum, boolean isExistAttach) {
        List<Post> posts = new ArrayList<>();

        if (isExistAttach) {
            for (int i = 1; i <= postNum; i++) {
                Post mockPost = createMockPostIncludeAttach((long) i, i + "", i);
                posts.add(mockPost);
            }
        } else {
            for (int i = 1; i <= postNum; i++) {
                Post mockPost = createMockPost((long) i, i + "");
                posts.add(mockPost);
            }
        }

        return Board.builder().name(boardName).posts(posts).build();
    }
}
