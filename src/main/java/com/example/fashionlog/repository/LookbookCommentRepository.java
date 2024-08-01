package com.example.fashionlog.repository;

import com.example.fashionlog.domain.LookbookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 룩북 게시판 Repository -> DB와 연동하여 LookbookComment의 데이터 CRUD 로직 구현.
 *
 * @author Hynss
 * @version 1.0.0
 */

@Repository
public interface LookbookCommentRepository extends JpaRepository<LookbookComment, Long> {
	List<LookbookComment> findAllByLookbookIdAndCommentStatusIsTrue(Long lookbookId);
}
