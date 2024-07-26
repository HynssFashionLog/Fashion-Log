package com.example.fashionlog.controller;

import com.example.fashionlog.dto.LookbookDto;
import com.example.fashionlog.service.LookbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("lookbooks", lookbookService.getAllLookbooks());
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
		model.addAttribute("lookbook", lookbookService.getLookbookById(id));
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
		return "redirect:/fashionlog/lookbook";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		lookbookService.deleteLookbook(id);
		return "redirect:/fashionlog/lookbook";
	}

}