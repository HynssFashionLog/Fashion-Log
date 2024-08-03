package com.example.fashionlog.controller.user;

import com.example.fashionlog.domain.Role;
import com.example.fashionlog.dto.MemberDto;
import com.example.fashionlog.service.MemberService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Member 컨트롤러
 *
 * @author Hynss
 * @version 1.0.0
 */
@Controller
@RequestMapping("/fashionlog")
@RequiredArgsConstructor
public class AdminUserManagementController {

	private final MemberService memberService;

	/**
	 * 회원 리스트 조회
	 */
	@GetMapping("/management")
	public String ManagementPage(Model model) {
		List<MemberDto> memberDto = memberService.getAllMembers()
			.orElse(Collections.emptyList());
		model.addAttribute("members", memberDto);
		return "management";
	}

	/**
	 * 회원 권한 수정
	 */
	@PostMapping("/management/{id}/{role}")
	public String updateMemberRole(@PathVariable("id") Long id,
		@PathVariable("role") Role role,
		@ModelAttribute MemberDto memberDto) {
		memberService.updateUserRole(id, role, memberDto);
		return "redirect:/fashionlog/management";
	}
}