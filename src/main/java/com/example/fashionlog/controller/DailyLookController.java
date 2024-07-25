package com.example.fashionlog.controller;

import com.example.fashionlog.service.DailyLookService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
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
}