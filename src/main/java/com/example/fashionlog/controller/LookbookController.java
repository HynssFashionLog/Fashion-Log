package com.example.fashionlog.controller;

import com.example.fashionlog.dto.LookbookCommentDto;
import com.example.fashionlog.dto.LookbookDto;
import com.example.fashionlog.service.LookbookService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fashionlog/lookbook")
public class LookbookController {

	private final LookbookService lookbookService;

	@Autowired
	public LookbookController(LookbookService lookbookService) {
		this.lookbookService = lookbookService;
	}

	@GetMapping
	public String list(Model model) {
		List<LookbookDto> lookbooks = lookbookService.getAllLookbooks();
		model.addAttribute("lookbooks", lookbooks);
		return "lookbook/list";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("lookbookDto", new LookbookDto());
		return "lookbook/new";
	}

	@PostMapping("/new")
	public String create(@ModelAttribute LookbookDto lookbookDto) {
		lookbookService.createLookbook(lookbookDto);
		return "redirect:/fashionlog/lookbook";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		LookbookDto lookbook = lookbookService.getLookbookById(id);
		List<LookbookCommentDto> comments = lookbookService.getCommentsByLookbookId(id);
		model.addAttribute("lookbook", lookbook);
		model.addAttribute("comments", comments);
		model.addAttribute("newComment", new LookbookCommentDto());
		return "lookbook/detail";
	}


	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("lookbookDto", lookbookService.getLookbookById(id));
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

	@PostMapping("/{lookbookId}/comments")
	public String createComment(@PathVariable Long lookbookId, @ModelAttribute LookbookCommentDto commentDto) {
		commentDto.setLookbookId(lookbookId);
		lookbookService.createComment(commentDto);
		return "redirect:/fashionlog/lookbook/" + lookbookId;
	}



	@PostMapping("/{lookbookId}/comments/{commentId}/delete")
	public String deleteComment(@PathVariable Long lookbookId, @PathVariable Long commentId) {
		try {
			lookbookService.deleteComment(commentId);
		} catch (RuntimeException e) {
			// 예외 처리 또는 로깅
			return "redirect:/fashionlog/lookbook/" + lookbookId; // 에러 발생 시 상세 페이지로 리다이렉트
		}
		return "redirect:/fashionlog/lookbook/" + lookbookId;
	}


	@PostMapping("/{lookbookId}/comments/{commentId}/edit")
	public String editComment(@PathVariable Long lookbookId, @PathVariable Long commentId, @ModelAttribute LookbookCommentDto commentDto) {
		commentDto.setId(commentId);
		commentDto.setLookbookId(lookbookId);
		lookbookService.updateComment(commentId, commentDto);
		return "redirect:/fashionlog/lookbook/" + lookbookId;
	}

}