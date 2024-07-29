package com.example.fashionlog.controller;

import com.example.fashionlog.dto.FreeBoardCommentDto;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.service.FreeBoardService;
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
@RequestMapping("/fashionlog/freeboard")
public class FreeBoardController {

	private final FreeBoardService freeBoardService;

	@Autowired
	public FreeBoardController(FreeBoardService freeBoardService) {
		this.freeBoardService = freeBoardService;
	}

	@GetMapping
	public String getAllFreeBoardList(Model model) {
		List<FreeBoardDto> freeBoardDto = freeBoardService.getAllFreeBoards()
			.orElse(Collections.emptyList());
		model.addAttribute("posts", freeBoardDto);
		return "freeboard/list";
	}

	@GetMapping("/new")
	public String newFreeBoardForm() {
		return "freeboard/form";
	}

	@PostMapping
	public String saveFreeBoardPost(@ModelAttribute FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.createFreeBoardPost(freeBoardDto);
			return "redirect:/fashionlog/freeboard"; // 성공 시 목록 페이지로 리다이렉트
		} catch (IllegalArgumentException e) {
			return "redirect:/fashionlog/freeboard/new"; // 예외 발생 시 같은 페이지 리다이렉트
		}
	}

	@GetMapping("/{id}")
	public String getFreeBoardById(@PathVariable("id") Long id, Model model) {
		Optional<FreeBoardDto> freeBoardDtoOpt = freeBoardService.getFreeBoardDtoById(id);
		// FreeBoard 정보가 없을 때 예외 처리
		if (freeBoardDtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Free board not found");
		}
		FreeBoardDto freeBoardDto = freeBoardDtoOpt.get();
		List<FreeBoardCommentDto> freeBoardCommentDto = freeBoardService.getCommentsByFreeBoardId(
			id).orElse(Collections.emptyList());

		model.addAttribute("post", freeBoardDto);
		model.addAttribute("comments", freeBoardCommentDto);

		return "freeboard/detail";
	}

	@GetMapping("/{id}/edit")
	public String editFreeBoardForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("post",
			freeBoardService.getFreeBoardDtoById(id).orElse(new FreeBoardDto()));
		return "freeboard/edit";
	}

	@PostMapping("/{id}/edit")
	public String editFreeBoardPost(@PathVariable("id") Long id,
		@ModelAttribute FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.updateFreeBoardPost(id, freeBoardDto);
			return "redirect:/fashionlog/freeboard/" + id; // 성공 시 상세 보기 페이지로 리다이렉트
		} catch (IllegalArgumentException e) {
			return "redirect:/fashionlog/freeboard/{id}/edit"; // 예외 발생 시 같은 페이지 리다이렉트
		}
	}

	@PostMapping("/{id}/delete")
	public String deleteFreeBoardPost(@PathVariable("id") Long id,
		@ModelAttribute FreeBoardDto freeBoardDto) {
		freeBoardService.deleteFreeBoardPost(id);
		return "redirect:/fashionlog/freeboard";
	}

	@PostMapping("/{postid}/comment")
	public String saveFreeBoardComment(@PathVariable("postid") Long postId,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.createFreeBoardComment(postId, freeBoardCommentDto);
			return "redirect:/fashionlog/freeboard/" + postId; // 성공 시 상세 보기 페이지로 리다이렉트
		} catch (IllegalArgumentException e) {
			return "redirect:/fashionlog/freeboard/" + postId; // 예외 발생 시 같은 페이지 리다이렉트
		}
	}

	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editFreeBoardComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.updateFreeBoardComment(commentId, freeBoardCommentDto);
			return "redirect:/fashionlog/freeboard/" + postId; // 성공 시 상세 보기 페이지로 리다이렉트
		} catch (IllegalArgumentException e) {
			return "redirect:/fashionlog/freeboard/" + postId; // 예외 발생 시 같은 페이지 리다이렉트
		}
	}

	@PostMapping("/{postid}/delete-comment/{commentid}")
	public String deleteFreeBoardComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId) {
		freeBoardService.deleteFreeBoardComment(commentId);
		return "redirect:/fashionlog/freeboard/" + postId;
	}
}