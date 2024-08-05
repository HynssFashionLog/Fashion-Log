package com.example.fashionlog.controller.board;

import com.example.fashionlog.aop.exception.BoardExceptionHandler;
import com.example.fashionlog.domain.Category;
import com.example.fashionlog.dto.board.DailyLookDto;
import com.example.fashionlog.dto.board.NoticeDto;
import com.example.fashionlog.dto.comment.DailyLookCommentDto;
import com.example.fashionlog.service.board.DailyLookService;
import com.example.fashionlog.service.board.NoticeService;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fashionlog/dailylook")
public class DailyLookController {

	private final DailyLookService dailyLookService;
	private final NoticeService noticeService;

	public DailyLookController(DailyLookService dailyLookService, NoticeService noticeService) {
		this.dailyLookService = dailyLookService;
		this.noticeService = noticeService;
	}

	@BoardExceptionHandler(boardType = "dailylook", errorRedirect = "redirect:/fashionlog")
	@GetMapping
	public String getAllDailyLookPost(Model model) {
		List<DailyLookDto> dailyLookDto = dailyLookService.getAllDailyLookPost()
			.orElse(Collections.emptyList());
		List<NoticeDto> noticeDto = noticeService.getNoticeListByCategory(Category.DAILY_LOOK)
			.orElse(Collections.emptyList());
		model.addAttribute("dailylooks", dailyLookDto);
		model.addAttribute("dailyLookNotice", noticeDto);
		return "dailylook/list";
	}

	@GetMapping("/new")
	public String getCreateDailyLookPostForm(Model model) {
		model.addAttribute("dailyLook", new DailyLookDto());
		return "dailylook/form";
	}

	@BoardExceptionHandler(boardType = "dailylook")
	@PostMapping("/new")
	public String saveDailyLookPost(@Valid @ModelAttribute("dailyLook") DailyLookDto dailyLookDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/dailylook/new";
		}

		dailyLookService.createDailyLookPost(dailyLookDto);
		return "redirect:/fashionlog/dailylook";
	}

	@BoardExceptionHandler(boardType = "dailylook", errorRedirect = "redirect:/fashionlog/dailylook")
	@GetMapping("/{id}")
	public String getDailyLookPostById(@PathVariable("id") Long id, Model model) {
		Optional<DailyLookDto> dailyLookDtoOpt = dailyLookService.getDailyLookPostById(id);
		// DailyLook 정보가 없을 때 예외 처리
		if (dailyLookDtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Daily Look not found");
		}
		DailyLookDto dailyLookDto = dailyLookDtoOpt.get();
		List<DailyLookCommentDto> dailyLookCommentDto = dailyLookService.getAllDailyLookCommentByDailyLookId(
			id).orElse(Collections.emptyList());

		model.addAttribute("dailyLook", dailyLookDto);
		model.addAttribute("dailyLookComments", dailyLookCommentDto);
		model.addAttribute("dailyLookComment", new DailyLookCommentDto());
		return "dailylook/detail";
	}

	@GetMapping("/{id}/edit")
	public String getDailyLookEdit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("dailyLook",
			dailyLookService.getDailyLookPostById(id).orElse(new DailyLookDto()));
		return "dailylook/edit";
	}

	@BoardExceptionHandler(boardType = "dailylook")
	@PostMapping("/{id}/edit")
	public String editDailyLookPost(@PathVariable("id") Long id,
		@Valid @ModelAttribute DailyLookDto dailyLookDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/dailylook/{id}/edit";
		}

		dailyLookService.editDailyLookPost(id, dailyLookDto);
		return "redirect:/fashionlog/dailylook/" + id;
	}

	@BoardExceptionHandler(boardType = "dailylook")
	@PostMapping("{id}/delete")
	public String deleteDailyPost(@PathVariable("id") Long id) {
		dailyLookService.deleteDailyLookPost(id);
		return "redirect:/fashionlog/dailylook";
	}

	@BoardExceptionHandler(boardType = "dailylook", isComment = true)
	@PostMapping("/{id}/comment")
	public String saveDailyLookComment(
		@PathVariable("id") Long postId,
		@Valid @ModelAttribute("dailyLookComment") DailyLookCommentDto dailyLookCommentDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/dailylook/" + postId;
		}

		dailyLookService.createDailyLookComment(postId, dailyLookCommentDto);
		return "redirect:/fashionlog/dailylook/" + postId;
	}

	@BoardExceptionHandler(boardType = "dailylook", isComment = true)
	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editDailyLookComment(
		@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@Valid @ModelAttribute("dailyLookComment") DailyLookCommentDto dailyLookCommentDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/dailylook/" + postId;
		}

		dailyLookService.editDailyLookComment(commentId, dailyLookCommentDto);
		return "redirect:/fashionlog/dailylook/" + postId;
	}

	@BoardExceptionHandler(boardType = "dailylook", isComment = true)
	@PostMapping("/{postid}/delete-comment/{commentid}")
	public String deleteDailyLookComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId) {
		dailyLookService.deleteDailyLookComment(commentId);
		return "redirect:/fashionlog/dailylook/" + postId;
	}
}