package com.example.fashionlog.repository.comment;

import com.example.fashionlog.domain.comment.TradeComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeCommentRepository extends JpaRepository<TradeComment, Long> {

	List<TradeComment> findAllByTradeIdAndCommentStatusIsTrue(Long tradeId);
}

