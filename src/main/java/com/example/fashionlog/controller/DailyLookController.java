package com.example.fashionlog.controller;

import com.example.fashionlog.dto.DailyLookDto;
import com.example.fashionlog.service.DailyLookService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fashionlog/dailylook")
public class DailyLookController {

    private final DailyLookService dailyLookService;

    public DailyLookController(DailyLookService dailyLookService) {
        this.dailyLookService = dailyLookService;
    }

    @GetMapping
    public String getAllDailyLookPost(Model model) {
        model.addAttribute("dailylooks", dailyLookService.getAllDailyLookPost());
        return "dailylook/list";
    }

    @GetMapping("/new")
    public String getCreateDailyLookPostForm(Model model) {
        model.addAttribute("dailyLook", new DailyLookDto());
        return "dailylook/form";
    }

    @PostMapping("/new")
    public String createDailyLookPostForm(@ModelAttribute("dailyLook") DailyLookDto dailyLookDto) {
        System.out.println("Received DTO: " + dailyLookDto);
        System.out.println("Content in Service: " + dailyLookDto.getContent());
        dailyLookService.createDailyLookPost(dailyLookDto);
        return "redirect:/fashionlog/dailylook";
    }

    @GetMapping("/{id}")
    public String getDailyLookPostById(@PathVariable Long id, Model model) {
        model.addAttribute("dailyLook", dailyLookService.getDailyLookPostById(id));
        return "dailylook/detail";
    }

    @GetMapping("/{id}/edit")
    public String getDailyLookEdit(@PathVariable Long id, Model model) {
        model.addAttribute("dailyLook", dailyLookService.getDailyLookPostById(id));
        return "dailylook/edit";
    }

    @PostMapping("/{id}/edit")
    public String editDailyLookPost(@PathVariable Long id,
        @ModelAttribute DailyLookDto dailyLookDto) {
        dailyLookService.editDailyLookPost(id, dailyLookDto);
        return "redirect:/fashionlog/dailylook";
    }

    @PostMapping("{id}/delete")
    public String deleteDailyPost(@PathVariable Long id) {
        dailyLookService.deleteDailyPost(id);
        return "redirect:/fashionlog/dailylook";
    }
}