package com.example.fashionlog.service;

import com.example.fashionlog.domain.Trade;
import com.example.fashionlog.domain.TradeComment;
import com.example.fashionlog.dto.TradeCommentDto;
import com.example.fashionlog.dto.TradeDto;
import com.example.fashionlog.repository.TradeCommentRepository;
import com.example.fashionlog.repository.TradeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {

	private final TradeRepository tradeRepository;
	private final TradeCommentRepository tradeCommentRepository;

	@Autowired
	public TradeService(TradeRepository tradeRepository,
		TradeCommentRepository tradeCommentRepository) {
		this.tradeRepository = tradeRepository;
		this.tradeCommentRepository = tradeCommentRepository;
	}

	/**
	 * 거래 게시판 글 목록 조회
	 *
	 * @return getPostStatus 가 true일 경우, 거래 게시판의 전체글을 반환
	 */
	public Optional<List<TradeDto>> getAllTrades() {
		List<TradeDto> trades = tradeRepository.findByDeletedAtIsNull().stream()
			.map(TradeDto::convertToDto)
			.collect(Collectors.toList());
		return trades.isEmpty() ? Optional.empty() : Optional.of(trades);
	}

	/**
	 * 거래 글 상세보기
	 *
	 * @param id 글 하나를 상세보기 하기위해 게시글 id를 받아옴
	 * @return 거래게시판 데이터베이스에서 id가 같은 게시글을 반환
	 */
	public Optional<TradeDto> getTradeById(Long id) {
		return tradeRepository.findByIdAndStatusIsTrue(id)
			.map(TradeDto::convertToDto);
	}

	/**
	 * 거래 게시판 글 작성하기
	 *
	 * @param tradeDto 컨트롤러에서 db에 삽입할 dto를 받아옴
	 */
	@Transactional
	public void createTradePost(TradeDto tradeDto) {
		// Not Null 예외 처리
		Trade.validateField(tradeDto.getTitle(), "Title");
		Trade.validateField(tradeDto.getContent(), "Content");
		tradeRepository.save(TradeDto.convertToEntity(tradeDto));
	}

	/**
	 * 거래 게시판 글 수정하기
	 *
	 * @param id       수정할 글의 id 받아오기
	 * @param tradeDto 컨트롤러에서 db에 수정할 dto 받아오기
	 */
	@Transactional
	public void updateTrade(Long id, TradeDto tradeDto) {
		// Not Null 예외처리
		Trade.validateField(tradeDto.getTitle(), "Title");
		Trade.validateField(tradeDto.getContent(), "Content");

		Trade trade = tradeRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		trade.update(tradeDto);
	}

	/**
	 * 거래 게시판 글 삭제하기
	 *
	 * @param id 삭제할 글의 id 받아오기
	 */
	@Transactional
	public void deleteTrade(Long id) {
		Trade trade = tradeRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		trade.delete();
	}

	/**
	 * 거래 게시판 글의 댓글 목록 조회
	 */
	public Optional<List<TradeCommentDto>> getCommentsByTradeId(Long tradeId){
		List<TradeCommentDto> comments = tradeCommentRepository
			.findAllByTradeIdAndCommentStatusIsTrue(tradeId).stream()
			.filter(TradeComment::getCommentStatus)
			.map(TradeCommentDto::convertToDto)
			.collect(Collectors.toList());
		return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
	}


	/**
	 * 거래 게시판 글의 댓글 작성하기
	 *
	 * @param id              거래 게시판 글의 id를 컨트롤러에서 받아옴
	 * @param tradeCommentDto 컨트롤러에서 db에 삽입할 댓글 dto를 받아옴
	 */
	@Transactional
	public void createTradeComment(Long id, TradeCommentDto tradeCommentDto) {
		// Not Null 예외 처리
		TradeComment.validateField(tradeCommentDto.getContent(), "Content");

		Trade trade = tradeRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		TradeComment tradeComment = TradeCommentDto.convertToEntity(trade, tradeCommentDto);
		tradeCommentRepository.save(tradeComment);
	}

	/**
	 * 거래 게시판 글의 댓글 수정하기
	 *
	 * @param commentId       거래 게시판 글의 댓글 id를 컨트롤러에서 받아오기
	 * @param tradeCommentDto 컨트롤러에서 db에 수정할 댓글 dto를 받아옴
	 */
	@Transactional
	public void updateTradeComment(Long commentId, TradeCommentDto tradeCommentDto) {
		// Not Null 예외 처리
		TradeComment.validateField(tradeCommentDto.getContent(), "Content");

		TradeComment tradeComment = tradeCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));
		tradeComment.updateComment(tradeCommentDto);
	}

	/**
	 * 거래 게시판 글의 댓글 삭제하기
	 *
	 * @param commentId 거래 게시판 글의 댓글 id를 컨트롤러에서 받아옴
	 */
	@Transactional
	public void deleteTradeComment(Long commentId) {
		TradeComment tradeComment = tradeCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));
		tradeComment.deleteComment();
	}

}