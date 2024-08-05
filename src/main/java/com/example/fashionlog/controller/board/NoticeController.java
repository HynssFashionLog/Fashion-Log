package com.example.fashionlog.controller.board;

import com.example.fashionlog.aop.exception.BoardExceptionHandler;
import com.example.fashionlog.domain.Category;
import com.example.fashionlog.domain.board.Notice;
import com.example.fashionlog.dto.board.NoticeDto;
import com.example.fashionlog.dto.comment.NoticeCommentDto;
import com.example.fashionlog.service.board.NoticeService;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fashionlog/notice")
public class NoticeController {

	private final NoticeService noticeService;

	@BoardExceptionHandler(boardType = "notice", errorRedirect = "redirect:/fashionlog")
	@GetMapping
	public String getAllNoticeList(Model model) {
		List<NoticeDto> noticeDto = noticeService.getAllNotices()
			.orElse(Collections.emptyList());
		model.addAttribute("notices", noticeDto);
		return "notice/list";
	}

	@GetMapping("/new")
	public String createNoticeForm(Model model) {
		model.addAttribute("notice", new Notice());
		model.addAttribute("categories", Category.values());
		return "notice/form";
	}

	@BoardExceptionHandler(boardType = "notice")
	@PostMapping("/new")
	public String saveNotice(@Valid @ModelAttribute NoticeDto noticeDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/notice/new";
		}

		noticeService.createNotice(noticeDto);
		return "redirect:/fashionlog/notice";
	}

	@BoardExceptionHandler(boardType = "notice", errorRedirect = "redirect:/fashionlog/notice")
	@GetMapping("/{id}")
	public String getNoticeById(@PathVariable Long id, Model model) {
		Optional<NoticeDto> noticeDtoOpt = noticeService.getNoticeDetail(id);
		if (noticeDtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Notice not found");
		}
		NoticeDto noticeDto = noticeDtoOpt.get();
		List<NoticeCommentDto> noticeCommentDto = noticeService.getNoticeCommentList(id)
			.orElse(Collections.emptyList());
		model.addAttribute("notice", noticeDto);
		model.addAttribute("noticeComment", noticeCommentDto);
		return "notice/detail";
	}

	@GetMapping("/{id}/edit")
	public String editNoticeForm(@PathVariable Long id, Model model) {
		model.addAttribute("notice", noticeService.getNoticeDetail(id).orElse(new NoticeDto()));
		model.addAttribute("categories", Category.values());
		return "notice/edit";
	}

	@BoardExceptionHandler(boardType = "notice")
	@PostMapping("/{id}/edit")
	public String editNotice(@PathVariable Long id, @Valid @ModelAttribute NoticeDto noticeDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/notice/{id}/edit";
		}

		noticeService.editNotice(id, noticeDto);
		return "redirect:/fashionlog/notice/" + id;
	}

	@BoardExceptionHandler(boardType = "notice")
	@PostMapping("/{id}/delete")
	public String deleteNotice(@PathVariable Long id) {
		noticeService.deleteNotice(id);
		return "redirect:/fashionlog/notice";
	}

	@BoardExceptionHandler(boardType = "notice", isComment = true)
	@PostMapping("/{id}/comment")
	public String saveNoticeComment(@PathVariable("id") Long postId,
		@Valid @ModelAttribute NoticeCommentDto noticeCommentDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/notice/" + postId;
		}

		noticeService.createNoticeComment(postId, noticeCommentDto);
		return "redirect:/fashionlog/notice/" + postId;
	}

	@BoardExceptionHandler(boardType = "notice", isComment = true)
	@PostMapping("/{postId}/edit-comment/{commentId}")
	public String editNoticeComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@Valid @ModelAttribute NoticeCommentDto noticeCommentDto, BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/notice/" + postId;
		}

		noticeService.editNoticeComment(postId, commentId, noticeCommentDto);
		return "redirect:/fashionlog/notice/" + postId;
	}

	@BoardExceptionHandler(boardType = "notice", isComment = true)
	@PostMapping("/{postId}/delete-comment/{commentId}")
	public String deleteNoticeComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId) {
		noticeService.deleteNoticeComment(commentId);
		return "redirect:/fashionlog/notice/" + postId;
	}
}

