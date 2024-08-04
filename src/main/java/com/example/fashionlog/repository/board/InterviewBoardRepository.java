package com.example.fashionlog.repository.board;

import com.example.fashionlog.domain.board.InterviewBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewBoardRepository extends JpaRepository<InterviewBoard, Long> {

	List<InterviewBoard> findAllByStatusIsTrue();

}

