package com.example.fashionlog.repository;

import com.example.fashionlog.domain.NoticeComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long> {

	List<NoticeComment> findAllByCommentStatusIsTrueAndNoticeId(Long id);

	Optional<NoticeComment> findByIdAndCommentStatusIsTrue(Long id);
}
