package com.example.fashionlog.controller;

import com.example.fashionlog.service.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String freeBoard(Model model) {
		model.addAttribute("freeBoards", freeBoardService.getAllFreeBoards());
		return "freeboard/list";
	}
}