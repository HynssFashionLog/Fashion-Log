package com.example.fashionlog.controller.user;

import com.example.fashionlog.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fashionlog")
public class WithdrawalController {
	private final MemberService memberService;

	@PostMapping("/withdrawal")
	public String withdrawal(HttpServletRequest request, HttpServletResponse response) {
		memberService.withdrawMember(request, response);
		return "redirect:/";
	}
}