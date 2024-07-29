package com.example.fashionlog.repository;

import com.example.fashionlog.domain.DailyLook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLookRepository extends JpaRepository<DailyLook, Long> {

    Optional<DailyLook> findByIdAndPostStatusIsTrue(Long id);

    List<DailyLook> findAllByPostStatusIsTrue();
}