package com.example.fashionlog.repository;

import com.example.fashionlog.domain.Notice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

	List<Notice> findAllByStatusIsTrueOrderByCreatedAtDesc();

	Optional<Notice> findByIdAndStatusIsTrue(Long id);
}
