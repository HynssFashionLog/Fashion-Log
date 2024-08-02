package com.example.fashionlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/fashionlog")
    public String showHome() {
        return "home";
    }

}
