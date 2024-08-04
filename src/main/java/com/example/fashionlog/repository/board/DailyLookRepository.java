package com.example.fashionlog.repository.board;

import com.example.fashionlog.domain.board.DailyLook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLookRepository extends JpaRepository<DailyLook, Long> {

    Optional<DailyLook> findByIdAndStatusIsTrue(Long id);

    List<DailyLook> findAllByStatusIsTrue();
}