package com.example.fashionlog.controller;

import com.example.fashionlog.domain.Category;
import com.example.fashionlog.domain.Notice;
import com.example.fashionlog.dto.NoticeCommentDto;
import com.example.fashionlog.dto.NoticeDto;
import com.example.fashionlog.service.NoticeService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@PostMapping("/new")
	public String createNotice(@ModelAttribute NoticeDto noticeDto) {
		noticeService.createNotice(noticeDto);
		return "redirect:/fashionlog/notice";
	}

	@GetMapping("/{id}")
	public String getNoticeById(@PathVariable Long id, Model model) {
		model.addAttribute("notice", noticeService.getNoticeDetail(id));
		model.addAttribute("noticeComment", noticeService.getNoticeCommentList(id));
		return "notice/detail";
	}

	@GetMapping("/{id}/edit")
	public String editNoticeForm(@PathVariable Long id, Model model) {
		model.addAttribute("notice", noticeService.getNoticeDetail(id));
		model.addAttribute("categories", Category.values());
		return "notice/edit";
	}

	@PostMapping("/{id}/edit")
	public String editNotice(@PathVariable Long id, @ModelAttribute NoticeDto noticeDto) {
		noticeService.editNotice(id, noticeDto);
		return "redirect:/fashionlog/notice/" + id;
	}

	@PostMapping("/{id}/delete")
	public String deleteNotice(@PathVariable Long id) {
		noticeService.deleteNotice(id);
		return "redirect:/fashionlog/notice";
	}

	@PostMapping("/{id}/comment")
	public String createNoticeComment(@PathVariable Long id, @ModelAttribute NoticeCommentDto noticeCommentDto) {
		noticeService.createNoticeComment(id, noticeCommentDto);
		return "redirect:/fashionlog/notice/" + id;
	}

	@PostMapping("/{postId}/edit-comment/{commentId}")
	public String editNoticeComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
		@ModelAttribute NoticeCommentDto noticeCommentDto) {
		noticeService.editNoticeComment(postId, commentId, noticeCommentDto);
		return "redirect:/fashionlog/notice/" + postId;
	}

	@PostMapping("/{postId}/delete-comment/{commentId}")
	public String deleteNoticeComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId) {
		noticeService.deleteNoticeComment(commentId);
		return "redirect:/fashionlog/notice/" + postId;
	}
}

