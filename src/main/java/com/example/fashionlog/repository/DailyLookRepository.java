package com.example.fashionlog.repository;

import com.example.fashionlog.domain.DailyLook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLookRepository extends JpaRepository<DailyLook, Long> {

}