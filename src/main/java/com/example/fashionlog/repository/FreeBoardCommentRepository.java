package com.example.fashionlog.repository;

import com.example.fashionlog.domain.FreeBoardComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {

	List<FreeBoardComment> findByFreeBoardId(Long freeBoardId);
}