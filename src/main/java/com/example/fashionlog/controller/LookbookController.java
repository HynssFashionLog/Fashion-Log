package com.example.fashionlog.controller;

import com.example.fashionlog.domain.Category;
import com.example.fashionlog.dto.LookbookCommentDto;
import com.example.fashionlog.dto.LookbookDto;
import com.example.fashionlog.dto.NoticeDto;
import com.example.fashionlog.service.LookbookService;
import com.example.fashionlog.service.NoticeService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@PostMapping("/new")
	public String create(@ModelAttribute LookbookDto lookbookDto) {
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

	@PostMapping("/{id}/edit")
	public String edit(@PathVariable Long id, @ModelAttribute LookbookDto lookbookDto) {
		lookbookService.updateLookbook(id, lookbookDto);
		return "redirect:/fashionlog/lookbook/" + id;
	}

	@PostMapping("/{id}/delete")
	public String deleteLookbook(@PathVariable Long id) {
		lookbookService.deleteLookbook(id);
		return "redirect:/fashionlog/lookbook";
	}

	@PostMapping("/{postid}/comment")
	public String createComment(@PathVariable("postid") Long postId,
		@ModelAttribute LookbookCommentDto lookbookCommentDto) {
		try {
			lookbookService.createLookbookComment(postId, lookbookCommentDto);
			return "redirect:/fashionlog/lookbook/" + postId;
		} catch (Exception e) {
			return "redirect:/fashionlog/lookbook/" + postId;
		}
	}


	@PostMapping("/{postid}/delete-comment/{commentid}")
	public String deleteComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId) {
		try {
			lookbookService.deleteLookbookComment(commentId);
		} catch (RuntimeException e) {
			// 예외 처리 또는 로깅
			return "redirect:/fashionlog/lookbook/" + postId; // 에러 발생 시 상세 페이지로 리다이렉트
		}
		return "redirect:/fashionlog/lookbook/" + postId;
	}


	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@ModelAttribute LookbookCommentDto lookbookCommentDto) {
		try {
			lookbookService.updateLookbookComment(commentId, lookbookCommentDto);
			return "redirect:/fashionlog/lookbook/" + postId;
		} catch (RuntimeException e) {
			return "redirect:/fashionlog/lookbook/" + postId;
		}
	}

}