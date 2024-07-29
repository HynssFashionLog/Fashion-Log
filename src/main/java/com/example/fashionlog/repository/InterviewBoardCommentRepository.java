package com.example.fashionlog.repository;

import com.example.fashionlog.domain.InterviewBoardComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewBoardCommentRepository extends
	JpaRepository<InterviewBoardComment, Long> {

	List<InterviewBoardComment> findByInterviewBoard_Id(Long id);
}
