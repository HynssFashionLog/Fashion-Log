package com.example.fashionlog.repository.comment;

import com.example.fashionlog.domain.comment.InterviewBoardComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewBoardCommentRepository extends
	JpaRepository<InterviewBoardComment, Long> {

	List<InterviewBoardComment> findAllByCommentStatusIsTrueAndInterviewBoardId(Long id);
}
