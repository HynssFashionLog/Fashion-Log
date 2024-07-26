package com.example.fashionlog.repository;

import com.example.fashionlog.domain.Lookbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookbookRepository extends JpaRepository<Lookbook, Long> {

}