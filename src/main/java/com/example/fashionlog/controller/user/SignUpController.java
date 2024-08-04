package com.example.fashionlog.controller.user;

import com.example.fashionlog.dto.MemberDto;
import com.example.fashionlog.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	// 회원가입시 중복체크
	@PostMapping("/sign-up/check-email")
	@ResponseBody
	public String checkEmailDuplicate(@RequestParam String email, HttpSession session) {
		boolean isDuplicate = memberService.isEmailDuplicate(email);
		session.setAttribute("emailChecked", email);
		session.setAttribute("isEmailAvailable", !isDuplicate);
		return isDuplicate ? "duplicate" : "available";
	}

	@PostMapping("/sign-up/check-nickname")
	@ResponseBody
	public String checkNicknameDuplicate(@RequestParam String nickname, HttpSession session) {
		boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
		session.setAttribute("nicknameChecked", nickname);
		session.setAttribute("isNicknameAvailable", !isDuplicate);
		return isDuplicate ? "duplicate" : "available";
	}

	@PostMapping("/sign-up/check-phone")
	@ResponseBody
	public String checkPhoneDuplicate(@RequestParam String phone, HttpSession session) {
		// 전화번호 형식 검증
		if (!phone.matches("010-\\d{4}-\\d{4}")) {
			return "올바른 전화번호 형식이 아닙니다";
		}

		boolean isDuplicate = memberService.isPhoneDuplicate(phone);
		session.setAttribute("phoneChecked", phone);
		session.setAttribute("isPhoneAvailable", !isDuplicate);
		return isDuplicate ? "duplicate" : "available";
	}

	/**
	 * 회원가입 요청을 처리합니다. 입력된 회원 정보의 유효성을 검사하고, 중복 여부를 확인한 후 새 회원을 등록합니다.
	 *
	 * @param memberDto 사용자가 입력한 회원가입 정보를 담은 DTO
	 * @param model     Spring MVC 모델
	 * @return 오류 발생 시 회원가입 페이지, 성공 시 로그인 페이지로 리다이렉트
	 */
	@PostMapping("/sign-up")
	public String getRegisterPage(@ModelAttribute("member") MemberDto memberDto,
		Model model) {
		try {
			memberService.createMember(memberDto);
			return "redirect:/fashionlog";
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "sign-up";
		}
	}
}