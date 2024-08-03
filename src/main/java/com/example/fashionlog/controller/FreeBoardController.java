package com.example.fashionlog.controller;

import com.example.fashionlog.dto.FreeBoardCommentDto;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.service.FreeBoardService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 자유 게시판 Controller -> FreeBoard API 요청/응답 로직 구현.
 *
 * @author Hynss
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/fashionlog/freeboard")
public class FreeBoardController {

	private final FreeBoardService freeBoardService;

	/**
	 * 자유 게시판 글 목록 조회
	 *
	 * @param model 모델로 뷰에 값을 전달해 줌.
	 * @return freeboard/list.html을 사용자 화면으로 반환
	 */
	@GetMapping
	public String getAllFreeBoardList(Model model) {
		try {
			List<FreeBoardDto> freeBoardDto = freeBoardService.getAllFreeBoards()
				.orElse(Collections.emptyList());
			model.addAttribute("freeboards", freeBoardDto);
			return "freeboard/list";
		} catch (SecurityException e) {
			return "home";
		}
	}

	/**
	 * 자유 게시판 글 작성 폼 조회
	 *
	 * @return freeboard/form.html을 사용자 화면으로 반환
	 */
	@GetMapping("/new")
	public String newFreeBoardForm(Model model) {
		model.addAttribute("freeBoard", new FreeBoardDto());
		return "freeboard/form";
	}

	/**
	 * 자유 게시판 글 작성
	 *
	 * @param freeBoardDto 폼에서 작성한 값들을 freeBoardDto로 받아옴.
	 * @return 제목과 내용이 비어있는지 안 비어있는지에 따라 각각의 대상 페이지에 리다이렉트
	 */
	@PostMapping("/new")
	public String saveFreeBoardPost(@ModelAttribute FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.createFreeBoardPost(freeBoardDto);
			return "redirect:/fashionlog/freeboard"; // 성공 시 목록 페이지로 리다이렉트
		} catch (IllegalArgumentException ie) {
			return "redirect:/fashionlog/freeboard/new"; // 예외 발생 시 같은 페이지 리다이렉트
		} catch (SecurityException se) {
			return "redirect:/fashionlog/freeboard/new";
		}
	}

	/**
	 * 자유 게시판 글 상세 조회
	 *
	 * @param id    자유 게시판 글 id
	 * @param model 모델로 뷰에 값을 전달해 줌.
	 * @return freeboard/detail.html을 사용자 화면으로 반환
	 */
	@GetMapping("/{id}")
	public String getFreeBoardById(@PathVariable("id") Long id, Model model) {
		try {
			Optional<FreeBoardDto> freeBoardDtoOpt = freeBoardService.getFreeBoardDtoById(id);
			// FreeBoard 정보가 없을 때 예외 처리
			if (freeBoardDtoOpt.isEmpty()) {
				throw new IllegalArgumentException("Free board not found");
			}
			FreeBoardDto freeBoardDto = freeBoardDtoOpt.get();
			List<FreeBoardCommentDto> freeBoardCommentDto = freeBoardService.getCommentsByFreeBoardId(
				id).orElse(Collections.emptyList());

			model.addAttribute("freeBoard", freeBoardDto);
			model.addAttribute("freeBoardComments", freeBoardCommentDto);
			model.addAttribute("freeBoardComment", new FreeBoardCommentDto());

			return "freeboard/detail";
		} catch (SecurityException e) {
			return "freeboard/list";
		}
	}

	/**
	 * 자유 게시판 글 수정 폼 조회
	 *
	 * @param id    자유 게시판 글 id
	 * @param model 모델로 뷰에 값을 전달해 줌.
	 * @return freeboard/edit.html을 사용자 화면으로 반환
	 */
	@GetMapping("/{id}/edit")
	public String editFreeBoardForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("freeBoard",
			freeBoardService.getFreeBoardDtoById(id).orElse(new FreeBoardDto()));
		return "freeboard/edit";
	}

	/**
	 * 자유 게시판 글 수정
	 *
	 * @param id           자유 게시판 글 id
	 * @param freeBoardDto 폼에서 작성한 값들을 freeBoardDto로 받아옴.
	 * @return 제목과 내용이 비어있는지 안 비어있는지에 따라 각각의 대상 페이지에 리다이렉트
	 */
	@PostMapping("/{id}/edit")
	public String editFreeBoardPost(@PathVariable("id") Long id,
		@ModelAttribute FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.updateFreeBoardPost(id, freeBoardDto);
			return "redirect:/fashionlog/freeboard/" + id; // 성공 시 상세 보기 페이지로 리다이렉트
		} catch (IllegalArgumentException ie) {
			return "redirect:/fashionlog/freeboard/{id}/edit"; // 예외 발생 시 같은 페이지 리다이렉트
		} catch (SecurityException se) {
			return "redirect:/fashionlog/freeboard/{id}/edit";
		}
	}

	/**
	 * 자유 게시판 글 삭제
	 *
	 * @param id 자유 게시판 글 id
	 * @return 자유 게시판 글 목록 페이지로 리다이렉트
	 */
	@PostMapping("/{id}/delete")
	public String deleteFreeBoardPost(@PathVariable("id") Long id,
		@ModelAttribute FreeBoardDto freeBoardDto) {
		try {
			freeBoardService.deleteFreeBoardPost(id, freeBoardDto);
			return "redirect:/fashionlog/freeboard";
		} catch (SecurityException e) {
			return "redirect:/fashionlog/freeboard/{id}";
		}
	}

	/**
	 * 자유 게시판 글에 댓글 작성
	 *
	 * @param postId              자유 게시판 글 id
	 * @param freeBoardCommentDto 폼에서 작성한 값들을 freeBoardCommentDto로 받아옴.
	 * @return 내용이 비어있는지 안 비어있는지에 확인 후 현재 페이지에 리다이렉트
	 */
	@PostMapping("/{postid}/comment")
	public String saveFreeBoardComment(@PathVariable("postid") Long postId,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.createFreeBoardComment(postId, freeBoardCommentDto);
			return "redirect:/fashionlog/freeboard/" + postId; // 성공 시 상세 보기 페이지로 리다이렉트
		} catch (IllegalArgumentException ie) {
			return "redirect:/fashionlog/freeboard/" + postId; // 예외 발생 시 같은 페이지 리다이렉트
		} catch (SecurityException se) {
			return "redirect:/fashionlog/freeboard/" + postId;
		}
	}

	/**
	 * 자유 게시판 글의 댓글 수정
	 *
	 * @param postId              자유 게시판 글 id
	 * @param commentId           자유 게시판 댓글 id
	 * @param freeBoardCommentDto 폼에서 작성한 값들을 freeBoardCommentDto로 받아옴.
	 * @return 내용이 비어있는지 안 비어있는지에 확인 후 현재 페이지에 리다이렉트
	 */
	@PostMapping("/{postid}/edit-comment/{commentid}")
	public String editFreeBoardComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		try {
			freeBoardService.updateFreeBoardComment(commentId, freeBoardCommentDto);
			return "redirect:/fashionlog/freeboard/" + postId; // 성공 시 상세 보기 페이지로 리다이렉트
		} catch (IllegalArgumentException ie) {
			return "redirect:/fashionlog/freeboard/" + postId; // 예외 발생 시 같은 페이지 리다이렉트
		} catch (SecurityException se) {
			return "redirect:/fashionlog/freeboard/" + postId;
		}
	}

	/**
	 * 자유 게시판 글의 댓글 삭제
	 *
	 * @param postId    자유 게시판 글 id
	 * @param commentId 자유 게시판 댓글 id
	 * @return 자유 게시판 글 상세 보기 페이지로 리다이렉트
	 */
	@PostMapping("/{postid}/delete-comment/{commentid}")
	public String deleteFreeBoardComment(@PathVariable("postid") Long postId,
		@PathVariable("commentid") Long commentId,
		@ModelAttribute FreeBoardCommentDto freeBoardCommentDto) {
		try {
			freeBoardService.deleteFreeBoardComment(commentId, freeBoardCommentDto);
			return "redirect:/fashionlog/freeboard/" + postId;
		} catch (SecurityException e) {
			return "redirect:/fashionlog/freeboard/" + postId;
		}
	}
}