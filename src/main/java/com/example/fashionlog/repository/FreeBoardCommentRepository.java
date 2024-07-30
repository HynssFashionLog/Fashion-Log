package com.example.fashionlog.repository;

import com.example.fashionlog.domain.FreeBoardComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 자유 게시판 Repository -> DB와 연동하여 FreeBoardComment의 데이터 CRUD 로직 구현.
 *
 * @author Hynss
 * @version 1.0.0
 */
public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {

	// 자유 게시판 id가 입력받은 id와 같고 삭제되지 않은 댓글들을 조회함.
	List<FreeBoardComment> findAllByFreeBoardIdAndCommentStatusIsTrue(Long id);
}