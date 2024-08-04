package com.example.fashionlog.controller.board;

import com.example.fashionlog.aop.exception.BoardExceptionHandler;
import com.example.fashionlog.domain.Category;
import com.example.fashionlog.domain.board.InterviewBoard;
import com.example.fashionlog.dto.board.InterviewBoardDto;
import com.example.fashionlog.dto.board.NoticeDto;
import com.example.fashionlog.dto.comment.InterviewBoardCommentDto;
import com.example.fashionlog.service.board.InterviewBoardService;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/fashionlog/interviewboard")
public class InterviewBoardController {

	private final InterviewBoardService interviewBoardService;
	private final NoticeService noticeService;

	@BoardExceptionHandler(boardType = "interviewboard", errorRedirect = "redirect:/fashionlog")
	@GetMapping
	public String getAllInterviewBoards(Model model) {
		List<InterviewBoardDto> interviewBoardDto = interviewBoardService.getAllInterviewPosts()
			.orElse(Collections.emptyList());
		List<NoticeDto> noticeDto = noticeService.getNoticeListByCategory(Category.INTERVIEW)
			.orElse(Collections.emptyList());
		model.addAttribute("interviewBoards", interviewBoardDto);
		model.addAttribute("interviewNotice", noticeDto);
		return "interviewboard/list";
	}

	@GetMapping("/new")
	public String newInterviewPostForm(Model model) {
		model.addAttribute("interviewPost", new InterviewBoard());
		return "interviewboard/form";
	}

	@BoardExceptionHandler(boardType = "interviewboard")
	@PostMapping("/new")
	public String saveInterviewPost(@Valid @ModelAttribute InterviewBoardDto interviewBoardDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/interviewboard/new";
		}

		interviewBoardService.createInterviewPost(interviewBoardDto);
		return "redirect:/fashionlog/interviewboard";
	}

	@BoardExceptionHandler(boardType = "interviewboard", errorRedirect = "redirect:/fashionlog/interviewboard")
	@GetMapping("/{id}")
	public String getInterviewBoardPostById(@PathVariable("id") Long postId, Model model) {
		Optional<InterviewBoardDto> interviewBoardDtoOpt = interviewBoardService.getInterviewPostDetail(
			postId);
		if (interviewBoardDtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Interview Board Not Found");
		}
		InterviewBoardDto interviewBoardDto = interviewBoardDtoOpt.get();
		List<InterviewBoardCommentDto> interviewBoardCommentDto = interviewBoardService.getCommentList(
			postId).orElse(Collections.emptyList());

		model.addAttribute("interviewPost", interviewBoardDto);
		model.addAttribute("interviewComment", interviewBoardCommentDto);

		return "interviewboard/detail";
	}

	@GetMapping("/{id}/edit")
	public String editInterviewPostForm(@PathVariable Long id, Model model) {
		model.addAttribute("interviewPost",
			interviewBoardService.getInterviewPostDetail(id).orElse(new InterviewBoardDto()));
		return "interviewboard/edit";
	}

	@BoardExceptionHandler(boardType = "interviewboard")
	@PostMapping("/{id}/edit")
	public String editInterviewPost(@PathVariable Long id,
		@Valid @ModelAttribute InterviewBoardDto interviewBoardDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/interviewboard/{id}/edit";
		}

		interviewBoardService.updateInterviewPost(id, interviewBoardDto);
		return "redirect:/fashionlog/interviewboard/{id}";
	}

	@BoardExceptionHandler(boardType = "interviewboard")
	@PostMapping("/{id}/delete")
	public String deleteInterviewPost(@PathVariable Long id) {
		interviewBoardService.deleteInterviewPost(id);
		return "redirect:/fashionlog/interviewboard";
	}

	@BoardExceptionHandler(boardType = "interviewboard", isComment = true)
	@PostMapping("/{postId}/comment")
	public String saveComment(@PathVariable("postId") Long postId,
		@Valid @ModelAttribute InterviewBoardCommentDto interviewBoardCommentDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/interviewboard/" + postId;
		}

		interviewBoardService.addComment(postId, interviewBoardCommentDto);
		return "redirect:/fashionlog/interviewboard/{postId}";
	}

	@BoardExceptionHandler(boardType = "interviewboard", isComment = true)
	@PostMapping("/{postId}/edit-comment/{commentId}")
	public String editComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@Valid @ModelAttribute InterviewBoardCommentDto interviewBoardCommentDto,
		BindingResult bindingResult) {
		// 글자 수 초과 예외 처리
		if (bindingResult.hasErrors()) {
			return "redirect:/fashionlog/interviewboard/" + postId;
		}

		interviewBoardService.updateInterviewComment(commentId, interviewBoardCommentDto);
		return "redirect:/fashionlog/interviewboard/" + postId;
	}

	@BoardExceptionHandler(boardType = "interviewboard", isComment = true)
	@PostMapping("/{postId}/delete-comment/{commentId}")
	public String deleteInterviewBoardComment(@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId) {
		interviewBoardService.deleteInterviewBoardComment(commentId);
		return "redirect:/fashionlog/interviewboard/" + postId;
	}
}