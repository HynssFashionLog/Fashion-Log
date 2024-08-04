package com.example.fashionlog.repository.board;

import com.example.fashionlog.domain.board.Lookbook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookbookRepository extends JpaRepository<Lookbook, Long> {
	List<Lookbook> findByDeletedAtIsNull();
	Optional<Lookbook> findByIdAndStatusIsTrue(Long id);
}