package com.example.fashionlog.controller.user;

import com.example.fashionlog.dto.MemberDto;
import com.example.fashionlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fashionlog")
public class SignUpController {

    private final MemberService memberService;

    public SignUpController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        model.addAttribute("member", new MemberDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String getRegisterPage(@ModelAttribute("member") MemberDto memberDto) {
        memberService.createMember(memberDto);
        return "redirect:/fashionlog";
    }
}
