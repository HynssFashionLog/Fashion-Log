package com.example.fashionlog.service;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Role;
import com.example.fashionlog.dto.MemberDto;
import com.example.fashionlog.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member 서비스
 *
 * @author Hynss
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final PersistentTokenRepository persistentTokenRepository;

	/**
	 * 회원 가입
	 *
	 * @param memberDto 컨트롤러에서 회원 정보를 DTO로 받아옴.
	 */
	@Transactional
	public void createMember(MemberDto memberDto) {
		// 중복 체크 항목 널 체크
		if (memberDto.getEmail() == null || memberDto.getNickname() == null ||
			memberDto.getPassword() == null || memberDto.getName() == null ||
			memberDto.getPhone() == null) {
			throw new IllegalArgumentException("All fields are required");
		}
		// 각 항목 중복 검사
		validateDuplicateValue("email", memberDto.getEmail());
		validateDuplicateValue("nickname", memberDto.getNickname());
		validateDuplicateValue("phone", memberDto.getPhone());

		memberDto.setStatus(Boolean.TRUE);
		memberDto.setRole(Role.NORMAL);
		memberDto.setCreatedAt(LocalDateTime.now());

		// 비밀번호 암호화
		String encodedPassword = getEncodedPassword(memberDto);
		memberDto.setPassword(encodedPassword);

		Member member = MemberDto.convertToEntity(memberDto);
		memberRepository.save(member);
	}


	private void validateDuplicateValue(String fieldName, String value) {
		boolean isDuplicate = switch (fieldName) {
			case "email" -> memberRepository.existsByEmail(value);
			case "nickname" -> memberRepository.existsByNickname(value);
			case "phone" -> memberRepository.existsByPhone(value);
			default -> throw new IllegalArgumentException("잘못된 필드 이름: " + fieldName);
		};

		if (isDuplicate) {
			throw new IllegalArgumentException(fieldName + "(" + value + ")" + "사용 중");
		}

	}

	/**
	 * 비밀번호 변환로직
	 */
	private String getEncodedPassword(MemberDto memberDto) {
		return passwordEncoder.encode(memberDto.getPassword());
	}

	/**
	 * 회원 정보 보기
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public Optional<List<MemberDto>> getAllMembers() {
		List<MemberDto> members = memberRepository.findAll().stream()
			.map(MemberDto::convertToDto)
			.toList();
		return members.isEmpty() ? Optional.empty() : Optional.of(members);
	}


	/**
	 * 회원 권한 정보 수정하기
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void updateUserRole(Long memberId, Role role, MemberDto memberDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));
		member.updateRole(memberDto, role);
	}

	/**
	 * 회원 탈퇴
	 */
	@Transactional
	public void withdrawMember(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmailAndStatusIsTrue(auth.getName());
		if (currentUser != null) {
			currentUser.deleteMember();

			// Remember Me 토큰 삭제
			persistentTokenRepository.removeUserTokens(auth.getName());

			// 로그아웃 처리
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	public boolean isNicknameDuplicate(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	public boolean isEmailDuplicate(String email) {
		return memberRepository.existsByEmail(email);
	}

	public boolean isPhoneDuplicate(String phone) {
		return memberRepository.existsByPhone(phone);
	}
}