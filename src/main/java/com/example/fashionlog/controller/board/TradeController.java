package com.example.fashionlog.controller.board;

import com.example.fashionlog.aop.exception.BoardExceptionHandler;
import com.example.fashionlog.domain.Category;
import com.example.fashionlog.dto.board.NoticeDto;
import com.example.fashionlog.dto.board.TradeDto;
import com.example.fashionlog.dto.comment.TradeCommentDto;
import com.example.fashionlog.service.board.NoticeService;
import com.example.fashionlog.service.board.TradeService;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fashionlog/trade")
public class TradeController {

	private final TradeService tradeService;
	private final NoticeService noticeService;

	@Autowired
	public TradeController(TradeService tradeService, NoticeService noticeService) {
		this.tradeService = tradeService;
		this.noticeService = noticeService;
	}

	/**
	 * 거래 게시판 글 목록 조회
	 *
	 * @param model 모델로 뷰에 값을 전달
	 */
	@BoardExceptionHandler(boardType = "trade", errorRedirect = "redirect:/fashionlog")
	@GetMapping
	public String getAllTradeList(Model model) {
		List<TradeDto> tradeDto = tradeService.getAllTrades()
			.orElse(Collections.emptyList());
		List<NoticeDto> noticeDto = noticeService.getNoticeListByCategory(Category.TRADE)
			.orElse(Collections.emptyList());
		model.addAttribute("trades", tradeDto);
		model.addAttribute("tradeNotice", noticeDto);
		return "trade/list";
	}

	/**
	 * 거래 게시판 글 상세 조회 detail.html 반환
	 */
	@BoardExceptionHandler(boardType = "trade", errorRedirect = "redirect:/fashionlog/trade")
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		Optional<TradeDto> tradeDtoOpt = tradeService.getTradeById(id);

		// Trade 정보가 없을 때 예외처리
		if (tradeDtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Trade not found");
		}
		TradeDto tradeDto = tradeDtoOpt.get();
		List<TradeCommentDto> tradeCommentDto = tradeService.getCommentsByTradeId(id)
			.orElse(Collections.emptyList());

		model.addAttribute("trade", tradeDto);
		model.addAttribute("tradeComments", tradeCommentDto);
		model.addAttribute("tradeComment", new TradeCommentDto());
		return "trade/detail";
	}

	/**
	 * 거래 게시판 글 작성 폼 (Get) new.html 반환
	 */
	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("trade", new TradeDto());
		return "trade/new";
	}

	/**
	 * 거래 게시판 글 작성 (Post)
	 */
	@BoardExceptionHandler(boardType = "trade")
	@PostMapping("/new")
	public String saveTradePost(@Valid @ModelAttribute TradeDto tradeDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/trade/new";
		}

		tradeService.createTradePost(tradeDto);
		return "redirect:/fashionlog/trade";
	}

	/**
	 * 거래 게시판 글 수정 폼 (Get)
	 */
	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("trade",
			tradeService.getTradeById(id).orElse(new TradeDto()));
		return "trade/editForm";
	}

	/**
	 * 거래 게시판 글 수정 (Post)
	 */
	@BoardExceptionHandler(boardType = "trade")
	@PostMapping("/{id}/edit")
	public String editTrade(@PathVariable Long id, @Valid @ModelAttribute TradeDto tradeDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/trade/{id}/edit";
		}

		tradeService.updateTrade(id, tradeDto);
		return "redirect:/fashionlog/trade/" + id;
	}

	/**
	 * 거래 게시판 글 삭제 (Post)
	 */
	@BoardExceptionHandler(boardType = "trade")
	@PostMapping("/{id}/delete")
	public String deleteTrade(@PathVariable Long id) {
		tradeService.deleteTrade(id);
		return "redirect:/fashionlog/trade";
	}

	/**
	 * 거래 게시판 글에 댓글 작성 (Post)
	 */
	@BoardExceptionHandler(boardType = "trade", isComment = true)
	@PostMapping("/{postid}/comment")
	public String saveComment(@PathVariable("postid") Long postId,
		@Valid @ModelAttribute TradeCommentDto tradeCommentDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/trade/" + postId;
		}

		tradeService.createTradeComment(postId, tradeCommentDto);
		return "redirect:/fashionlog/trade/" + postId;
	}

	/**
	 * 거래 게시판 글의 댓글 삭제 (Post)
	 */
	@BoardExceptionHandler(boardType = "trade", isComment = true)
	@PostMapping("/{postid}/delete-comment/{commentid}")
	public String deleteComment(@PathVariable("postid") Long postId,
		@PathVariable Long commentid) {
		tradeService.deleteTradeComment(commentid);
		return "redirect:/fashionlog/trade/" + postId;
	}

	/**
	 * 거래 게시판 글의 댓글 수정 (Post)
	 */
	@BoardExceptionHandler(boardType = "trade", isComment = true)
	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@Valid @ModelAttribute TradeCommentDto tradeCommentDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/trade/" + postId;
		}

		tradeService.updateTradeComment(commentId, tradeCommentDto);
		return "redirect:/fashionlog/trade/" + postId;
	}
}
