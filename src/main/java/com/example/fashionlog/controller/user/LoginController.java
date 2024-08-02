package com.example.fashionlog.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fashionlog")
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}
