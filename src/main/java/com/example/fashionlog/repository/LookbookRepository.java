package com.example.fashionlog.repository;

import com.example.fashionlog.domain.Lookbook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookbookRepository extends JpaRepository<Lookbook, Long> {
	List<Lookbook> findByDeletedAtIsNull();
	Lookbook findByIdAndDeletedAtIsNull(Long id);
}