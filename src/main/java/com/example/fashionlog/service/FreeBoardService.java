package com.example.fashionlog.service;

import com.example.fashionlog.aop.AuthCheck;
import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.domain.FreeBoardComment;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.dto.FreeBoardCommentDto;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.repository.FreeBoardCommentRepository;
import com.example.fashionlog.repository.FreeBoardRepository;
import com.example.fashionlog.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 자유 게시판 Service -> FreeBoard API 비즈니스 로직 구현.
 *
 * @author Hynss
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FreeBoardService {

	private final FreeBoardRepository freeBoardRepository;
	private final FreeBoardCommentRepository freeBoardCommentRepository;
	private final MemberRepository memberRepository;

	/**
	 * 자유 게시판 글 목록 조회하기 API
	 *
	 * @return getPostStatus(글이 삭제되었는지, soft delete 방식)가 true일 경우 자유 게시판의 전체 글을 반환함.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	public Optional<List<FreeBoardDto>> getAllFreeBoards() {
		List<FreeBoardDto> freeBoards = freeBoardRepository.findAllByStatusIsTrue().stream()
			.map(FreeBoardDto::convertToDto)
			.collect(Collectors.toList());
		return freeBoards.isEmpty() ? Optional.empty() : Optional.of(freeBoards);
	}

	/**
	 * 자유 게시판 글 작성하기 API
	 *
	 * @param freeBoardDto 컨트롤러에서 DB에 삽입할 DTO를 받아옴.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	@Transactional
	public void createFreeBoardPost(FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		FreeBoard.validateField(freeBoardDto.getTitle(), "Title");
		FreeBoard.validateField(freeBoardDto.getContent(), "Content");

		// 현재 회원 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmail(auth.getName());

		freeBoardDto.setMemberId(currentUser.getMemberId());

		freeBoardRepository.save(FreeBoardDto.convertToEntity(freeBoardDto));
	}

	/**
	 * 자유 게시판 글 상세보기 API
	 *
	 * @param id 글 하나를 상세하게 보기위해 게시글 id를 받아옴.
	 * @return 자유 게시판 데이터베이스에서 id가 같은 게시글을 반환함.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	public Optional<FreeBoardDto> getFreeBoardDtoById(Long id) {
		return freeBoardRepository.findByIdAndStatusIsTrue(id)
			.map(FreeBoardDto::convertToDto);
	}

	/**
	 * 자유 게시판 글 수정하기 API
	 *
	 * @param id           수정할 글의 id를 받아옴.
	 * @param freeBoardDto 컨트롤러에서 DB에 수정할 DTO를 받아옴.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	@Transactional
	public void updateFreeBoardPost(Long id, FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		FreeBoard.validateField(freeBoardDto.getTitle(), "Title");
		FreeBoard.validateField(freeBoardDto.getContent(), "Content");

		// 현재 회원 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmail(auth.getName());

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));

		// 현재 회원 id가 글쓴이 id와 같거나 관리자인지 확인
		if (currentUser.getMemberId().equals(freeBoard.getMemberId())) {
			freeBoard.update(freeBoardDto);
		} else if (auth.getAuthorities().stream()
			.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			freeBoard.update(freeBoardDto);
		} else {
			throw new SecurityException("권한이 없습니다.");
		}
	}

	/**
	 * 자유 게시판 글 삭제하기 API
	 *
	 * @param id 삭제할 글의 id를 받아옴.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	@Transactional
	public void deleteFreeBoardPost(Long id, FreeBoardDto freeBoardDto) {
		// 현재 회원 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmail(auth.getName());

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));

		// 현재 회원 id가 글쓴이 id와 같거나 관리자인지 확인
		if (currentUser.getMemberId().equals(freeBoard.getMemberId())) {
			freeBoard.delete();
		} else if (auth.getAuthorities().stream()
			.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			freeBoard.delete();
		} else {
			throw new SecurityException("권한이 없습니다.");
		}
	}

	/**
	 * 자유 게시판 글의 댓글 목록 조회하기 API
	 *
	 * @param freeBoardId 댓글 들이 속한 자유 게시판 글의 id를 받아옴.
	 * @return 자유 게시판에서 조회한 글에 해당하는 댓글 목록을 반환함.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	public Optional<List<FreeBoardCommentDto>> getCommentsByFreeBoardId(Long freeBoardId) {
		List<FreeBoardCommentDto> comments = freeBoardCommentRepository
			.findAllByFreeBoardIdAndCommentStatusIsTrue(freeBoardId).stream()
			.filter(FreeBoardComment::getCommentStatus)
			.map(FreeBoardCommentDto::convertToDto)
			.collect(Collectors.toList());
		return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
	}

	/**
	 * 자유 게시판 글의 댓글 작성하기 API
	 *
	 * @param id                  자유 게시판 글의 id를 컨트롤러에서 받아옴.
	 * @param freeBoardCommentDto 컨트롤러에서 DB에 삽입할 댓글 DTO를 받아옴.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	@Transactional
	public void createFreeBoardComment(Long id, FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		FreeBoardComment.validateField(freeBoardCommentDto.getContent(), "Content");

		// 현재 회원 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmail(auth.getName());

		freeBoardCommentDto.setMemberId(currentUser.getMemberId());

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		FreeBoardComment freeBoardComment = FreeBoardCommentDto.convertToEntity(freeBoard,
			freeBoardCommentDto);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	/**
	 * 자유 게시판 글의 댓글 수정하기 API
	 *
	 * @param commentId           자유 게시판 글의 댓글 id를 컨트롤러에서 받아옴.
	 * @param freeBoardCommentDto 컨트롤러에서 DB에 수정할 댓글 DTO를 받아옴.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	@Transactional
	public void updateFreeBoardComment(Long commentId,
		FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		FreeBoardComment.validateField(freeBoardCommentDto.getContent(), "Content");

		// 현재 회원 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmail(auth.getName());

		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(
				() -> new IllegalArgumentException("comment id:" + commentId + " not found"));

		// 현재 회원 id가 글쓴이 id와 같거나 관리자인지 확인
		if (currentUser.getMemberId().equals(freeBoardComment.getMemberId())) {
			freeBoardComment.updateComment(freeBoardCommentDto);
		} else if (auth.getAuthorities().stream()
			.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			freeBoardComment.updateComment(freeBoardCommentDto);
		} else {
			throw new SecurityException("권한이 없습니다.");
		}
	}

	/**
	 * 자유 게시판 글의 댓글 삭제하기 API
	 *
	 * @param commentId 자유 게시판 글의 댓글 id를 컨트롤러에서 받아옴.
	 */
	@AuthCheck({"ADMIN", "NORMAL"})
	@Transactional
	public void deleteFreeBoardComment(Long commentId, FreeBoardCommentDto freeBoardCommentDto) {
		// 현재 회원 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member currentUser = memberRepository.findByEmail(auth.getName());

		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));

		// 현재 회원 id가 글쓴이 id와 같거나 관리자인지 확인
		if (currentUser.getMemberId().equals(freeBoardComment.getMemberId())) {
			freeBoardComment.deleteComment();
		} else if (auth.getAuthorities().stream()
			.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			freeBoardComment.deleteComment();
		} else {
			throw new SecurityException("권한이 없습니다.");
		}
	}
}