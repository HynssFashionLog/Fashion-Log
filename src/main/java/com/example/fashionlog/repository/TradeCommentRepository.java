package com.example.fashionlog.repository;

import com.example.fashionlog.domain.TradeComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeCommentRepository extends JpaRepository<TradeComment, Long> {

	List<TradeComment> findAllByTradeIdAndCommentStatusIsTrue(Long tradeId);
}

