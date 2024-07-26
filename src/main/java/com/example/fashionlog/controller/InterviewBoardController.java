package com.example.fashionlog.controller;

import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.dto.InterviewBoardDto;
import com.example.fashionlog.service.InterviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fashionlog/interviewboard")
public class InterviewBoardController {

	private final InterviewBoardService interviewBoardService;

	@Autowired
	public InterviewBoardController(InterviewBoardService interviewBoardService) {
		this.interviewBoardService = interviewBoardService;
	}

	@GetMapping
	public String getAllInterviewBoards(Model model) {
		model.addAttribute("interviewBoards", interviewBoardService.getAllInterviewPosts());
		return "interviewboard/list";
	}

	@GetMapping("/new")
	public String newInterviewPostForm(Model model) {
		model.addAttribute("interviewPost", new InterviewBoard());
		return "interviewboard/form";
	}

	@PostMapping("/new")
	public String newInterviewPost(@ModelAttribute InterviewBoardDto interviewBoardDto) {
		interviewBoardService.createInterviewPost(interviewBoardDto);
		return "redirect:/fashionlog/interviewboard";
	}

	@GetMapping("/{id}")
	public String getInterviewBoardPostById(@PathVariable Long id, Model model) {
		model.addAttribute("interviewPost", interviewBoardService.getInterviewPostDetail(id));
		return "interviewboard/detail";
	}
}

