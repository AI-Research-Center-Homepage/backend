package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    //List<NewsAndNoticePreviewMapping> findNewById();

    @Modifying
    @Query("update Post p set p.viewNum = p.viewNum + 1 where p.id = :id")
    int updateView(Long id);
}
