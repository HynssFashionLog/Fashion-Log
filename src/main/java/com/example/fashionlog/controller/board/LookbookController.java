package com.example.fashionlog.controller.board;

import com.example.fashionlog.aop.exception.BoardExceptionHandler;
import com.example.fashionlog.domain.Category;
import com.example.fashionlog.dto.board.LookbookDto;
import com.example.fashionlog.dto.board.NoticeDto;
import com.example.fashionlog.dto.comment.LookbookCommentDto;
import com.example.fashionlog.service.board.LookbookService;
import com.example.fashionlog.service.board.NoticeService;
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
@RequestMapping("/fashionlog/lookbook")
public class LookbookController {

	private final LookbookService lookbookService;
	private final NoticeService noticeService;

	@Autowired
	public LookbookController(LookbookService lookbookService, NoticeService noticeService) {
		this.lookbookService = lookbookService;
		this.noticeService = noticeService;
	}

	/**
	 * 룩북 게시판 글 목록 조회
	 *
	 * @param model 모델로 뷰에 값을 전달해 줌.
	 * @return lookbook/list.html을 사용자 화면으로 반환
	 */
	@BoardExceptionHandler(boardType = "lookbook", errorRedirect = "redirect:/fashionlog")
	@GetMapping
	public String getAllLookbookList(Model model) {
		List<LookbookDto> lookbookDto = lookbookService.getAllLookbooks()
			.orElse(Collections.emptyList());
		List<NoticeDto> noticeDto = noticeService.getNoticeListByCategory(Category.LOOKBOOK)
			.orElse(Collections.emptyList());
		model.addAttribute("lookbooks", lookbookDto);
		model.addAttribute("lookbookNotice", noticeDto);
		return "lookbook/list";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("lookBook", new LookbookDto());
		return "lookbook/new";
	}

	@BoardExceptionHandler(boardType = "lookbook")
	@PostMapping("/new")
	public String saveLookbookPost(@Valid @ModelAttribute LookbookDto lookbookDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/lookbook/new";
		}

		lookbookService.createLookbookPost(lookbookDto);
		return "redirect:/fashionlog/lookbook";
	}

	/**
	 * 룩북 게시판 글 상세 조회
	 *
	 * @param id    룩북 게시판 글 id
	 * @param model 모델로 뷰에 값을 전달해 줌.
	 * @return lookbook/detail.html을 사용자 화면으로 반환
	 */
	@BoardExceptionHandler(boardType = "lookbook", errorRedirect = "redirect:/fashionlog/lookbook")
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		Optional<LookbookDto> lookbookDtoOpt = lookbookService.getLookbookById(id);

		// Lookbook 정보가 없을 때 예외 처리
		if (lookbookDtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Lookbook not found");
		}
		LookbookDto lookbookDto = lookbookDtoOpt.get();
		List<LookbookCommentDto> lookbookCommentDto = lookbookService.getCommentsByLookbookId(
			id).orElse(Collections.emptyList());

		model.addAttribute("lookBook", lookbookDto);
		model.addAttribute("lookBookComments", lookbookCommentDto);
		model.addAttribute("lookBookComment", new LookbookCommentDto());
		return "lookbook/detail";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("lookBook",
			lookbookService.getLookbookById(id).orElse(new LookbookDto()));
		return "lookbook/editForm";
	}

	@BoardExceptionHandler(boardType = "lookbook")
	@PostMapping("/{id}/edit")
	public String editLookbookPost(@PathVariable Long id,
		@Valid @ModelAttribute LookbookDto lookbookDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/lookbook/{id}/edit";
		}

		lookbookService.updateLookbook(id, lookbookDto);
		return "redirect:/fashionlog/lookbook/" + id;
	}

	@BoardExceptionHandler(boardType = "lookbook")
	@PostMapping("/{id}/delete")
	public String deleteLookbook(@PathVariable Long id) {
		lookbookService.deleteLookbook(id);
		return "redirect:/fashionlog/lookbook";
	}

	@BoardExceptionHandler(boardType = "lookbook", isComment = true)
	@PostMapping("/{postid}/comment")
	public String saveComment(@PathVariable("postid") Long postId,
		@Valid @ModelAttribute LookbookCommentDto lookbookCommentDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/lookbook/" + postId;
		}

		lookbookService.createLookbookComment(postId, lookbookCommentDto);
		return "redirect:/fashionlog/lookbook/" + postId;
	}

	@BoardExceptionHandler(boardType = "lookbook", isComment = true)
	@PostMapping("/{postid}/delete-comment/{commentid}")
	public String deleteComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId) {
		lookbookService.deleteLookbookComment(commentId);
		return "redirect:/fashionlog/lookbook/" + postId;
	}

	@BoardExceptionHandler(boardType = "lookbook", isComment = true)
	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@Valid @ModelAttribute LookbookCommentDto lookbookCommentDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/lookbook/" + postId;
		}

		lookbookService.updateLookbookComment(commentId, lookbookCommentDto);
		return "redirect:/fashionlog/lookbook/" + postId;
	}

}