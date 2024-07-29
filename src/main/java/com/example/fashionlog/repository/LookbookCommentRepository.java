package com.example.fashionlog.repository;

import com.example.fashionlog.domain.LookbookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookbookCommentRepository extends JpaRepository<LookbookComment, Long> {
	List<LookbookComment> findByLookbookId(Long lookbookId);
}
