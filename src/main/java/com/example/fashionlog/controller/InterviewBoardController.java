package com.example.fashionlog.controller;

import com.example.fashionlog.service.InterviewBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}

