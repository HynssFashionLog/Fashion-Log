package com.example.fashionlog.repository;

import com.example.fashionlog.domain.FreeBoard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 자유 게시판 Repository -> DB와 연동하여 FreeBoard의 데이터 CRUD 로직 구현.
 *
 * @author Hynss
 * @version 1.0.0
 */
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

	// 자유 게시판 id가 같고 삭제되지 않은 게시물들을 조회함.
	Optional<FreeBoard> findByIdAndPostStatusIsTrue(Long id);

	// 삭제되지 않은 게시물들을 조회함.
	List<FreeBoard> findAllByPostStatusIsTrue();
}