package com.example.fashionlog.repository.comment;

import com.example.fashionlog.domain.comment.DailyLookComment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLookCommentRepository extends JpaRepository<DailyLookComment, Long> {

    List<DailyLookComment> findAllByDailyLookIdAndCommentStatusIsTrue(Long id);
}