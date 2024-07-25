package com.example.fashionlog.repository;

import com.example.fashionlog.domain.InterviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewBoardRepository extends JpaRepository<InterviewBoard, Long> {

}

