package byuntil.backend.post.domain.repository;

import byuntil.backend.post.domain.entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticePostRepository extends JpaRepository<NoticePost, Long> {
    //List<NewsAndNoticePreviewMapping> findAllNotice();
    @Modifying
    @Query("update NewsPost p set p.viewNum = p.viewNum + 1 where p.id = :id")
    int updateView(Long id);
}
