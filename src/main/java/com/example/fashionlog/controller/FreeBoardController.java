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
		Optional<List<FreeBoardDto>> freeBoardOpt = freeBoardService.getAllFreeBoards();
		List<FreeBoardDto> freeBoardDto = freeBoardOpt.orElse(Collections.emptyList());
		model.addAttribute("posts", freeBoardDto);
		return "freeboard/list";
	}

	@GetMapping("/new")
	public String newFreeBoardForm() {
		return "freeboard/form";
	}

	@PostMapping
	public String saveFreeBoardPost(@ModelAttribute FreeBoardDto freeBoardDto) {
		freeBoardService.createFreeBoardPost(freeBoardDto);
		return "redirect:/fashionlog/freeboard";
	}

	@GetMapping("/{id}")
	public String getFreeBoardById(@PathVariable("id") Long id, Model model) {
		Optional<FreeBoardDto> freeBoardOpt = freeBoardService.getFreeBoardDtoById(id);
		Optional<List<FreeBoardCommentDto>> freeBoardCommentOpt = freeBoardService.getCommentsByFreeBoardId(id);

		FreeBoardDto freeBoardDto = freeBoardOpt.orElse(new FreeBoardDto());
		List<FreeBoardCommentDto> freeBoardCommentDto = freeBoardCommentOpt.orElse(Collections.emptyList());

		model.addAttribute("post", freeBoardDto);
		model.addAttribute("comments", freeBoardCommentDto);
		model.addAttribute("postExists", freeBoardOpt.isPresent());

		return "freeboard/detail";
	}

	@GetMapping("/{id}/edit")
	public String editFreeBoardForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("post", freeBoardService.getFreeBoardDtoById(id));
		return "freeboard/edit";
	}

	@PostMapping("/{id}/edit")
	public String editFreeBoardPost(@PathVariable("id") Long id,
		@ModelAttribute FreeBoardDto freeBoardDto) {
		freeBoardService.updateFreeBoardPost(id, freeBoardDto);
		return "redirect:/fashionlog/freeboard/{id}";
	}

	@PostMapping("/{id}/delete")
	public String deleteFreeBoardPost(@PathVariable("id") Long id) {
		freeBoardService.deleteFreeBoardPost(id);
		return "redirect:/fashionlog/freeboard";
	}

	@PostMapping("/{postid}/comment")
	public String saveFreeBoardComment(@PathVariable("postid") Long id,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		freeBoardService.createFreeBoardComment(id, freeBoardCommentDto);
		return "redirect:/fashionlog/freeboard/{postid}";
	}

	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editFreeBoardComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		freeBoardService.updateFreeBoardComment(postId, commentId, freeBoardCommentDto);
		return "redirect:/fashionlog/freeboard/{postid}";
	}
}