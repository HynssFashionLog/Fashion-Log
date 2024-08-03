package com.example.fashionlog.service;

import com.example.fashionlog.aop.AuthCheck;
import com.example.fashionlog.aop.AuthorType;
import com.example.fashionlog.domain.Lookbook;
import com.example.fashionlog.domain.LookbookComment;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.dto.LookbookCommentDto;
import com.example.fashionlog.dto.LookbookDto;
import com.example.fashionlog.repository.LookbookCommentRepository;
import com.example.fashionlog.repository.LookbookRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LookbookService implements BoardService {

	private final LookbookRepository lookbookRepository;
	private final LookbookCommentRepository lookbookCommentRepository;
	private final CurrentUserProvider currentUserProvider;

	/**
	 * 룩북 게시판 글 목록 조회하기 API
	 *
	 * @return getPostStatus(글이 삭제되었는지, soft delete 방식)가 true일 경우 룩북 게시판의 전체 글을 반환함.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	public Optional<List<LookbookDto>> getAllLookbooks() {
		List<LookbookDto> lookbooks = lookbookRepository.findByDeletedAtIsNull().stream()
			.map(LookbookDto::convertToDto)
			.collect(Collectors.toList());
		return lookbooks.isEmpty() ? Optional.empty() : Optional.of(lookbooks);
	}

	/**
	 * 룩북 글 상세보기 API
	 *
	 * @param id 글 하나를 상세하게 보기위해 게시글 id를 받아옴.
	 * @return 룩북 게시판 데이터베이스에서 id가 같은 게시글을 반환함.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	public Optional<LookbookDto> getLookbookById(Long id) {
		return lookbookRepository.findByIdAndStatusIsTrue(id)
			.map(LookbookDto::convertToDto);
	}

	/**
	 * 룩북 게시판 글 작성하기 API
	 *
	 * @param lookbookDto 컨트롤러에서 DB에 삽입할 DTO를 받아옴.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void createLookbookPost(LookbookDto lookbookDto) {
		// Not Null 예외 처리
		Lookbook.validateField(lookbookDto.getTitle(), "Title");
		Lookbook.validateField(lookbookDto.getContent(), "Content");

		// 현재 로그인된 사용자를 가져옵니다
		Member currentUser = currentUserProvider.getCurrentUser();
		lookbookRepository.save(LookbookDto.convertToEntity(lookbookDto, currentUser));
	}

	/**
	 * 룩북 게시판 글 수정하기 API
	 *
	 * @param id          수정할 글의 id를 받아옴.
	 * @param lookbookDto 컨트롤러에서 DB에 수정할 DTO를 받아옴.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void updateLookbook(Long id, LookbookDto lookbookDto) {
		// Not Null 예외 처리
		Lookbook.validateField(lookbookDto.getTitle(), "Title");
		Lookbook.validateField(lookbookDto.getContent(), "Content");

		Lookbook lookbook = lookbookRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		lookbook.update(lookbookDto);
	}

	/**
	 * 룩북 게시판 글 상세보기 API
	 *
	 * @param id 글 하나를 상세하게 보기위해 게시글 id를 받아옴.
	 * @return 룩북 게시판 데이터베이스에서 id가 같은 게시글을 반환함.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	public Optional<LookbookDto> getLookbookDtoById(Long id) {
		return lookbookRepository.findByIdAndStatusIsTrue(id)
			.map(LookbookDto::convertToDto);
	}


	/**
	 * 룩북 게시판 글 삭제하기 API
	 *
	 * @param id 삭제할 글의 id를 받아옴.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void deleteLookbook(Long id) {
		Lookbook lookbook = lookbookRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		lookbook.delete();
	}

	/**
	 * 룩북 게시판 글의 댓글 목록 조회하기 API
	 *
	 * @param lookbookId 댓글 들이 속한 룩북 게시판 글의 id를 받아옴.
	 * @return 룩북 게시판에서 조회한 글에 해당하는 댓글 목록을 반환함.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	public Optional<List<LookbookCommentDto>> getCommentsByLookbookId(Long lookbookId) {
		List<LookbookCommentDto> comments = lookbookCommentRepository
			.findAllByLookbookIdAndCommentStatusIsTrue(lookbookId).stream()
			.filter(LookbookComment::getCommentStatus)
			.map(LookbookCommentDto::convertToDto)
			.collect(Collectors.toList());
		return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
	}

	/**
	 * 룩북 게시판 글의 댓글 작성하기 API
	 *
	 * @param id                 룩북 게시판 글의 id를 컨트롤러에서 받아옴.
	 * @param lookbookCommentDto 컨트롤러에서 DB에 삽입할 댓글 DTO를 받아옴.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void createLookbookComment(Long id, LookbookCommentDto lookbookCommentDto) {
		// Not Null 예외 처리
		LookbookComment.validateField(lookbookCommentDto.getContent(), "Content");

		Lookbook lookbook = lookbookRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		Member currentUser = currentUserProvider.getCurrentUser();

		LookbookComment lookbookComment = LookbookCommentDto.convertToEntity(lookbook,
			lookbookCommentDto, currentUser);
		lookbookCommentRepository.save(lookbookComment);
	}

	/**
	 * 룩북 게시판 글의 댓글 수정하기 API
	 *
	 * @param commentId          자유 게시판 글의 댓글 id를 컨트롤러에서 받아옴.
	 * @param lookbookCommentDto 컨트롤러에서 DB에 수정할 댓글 DTO를 받아옴.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void updateLookbookComment(Long commentId,
		LookbookCommentDto lookbookCommentDto) {
		// Not Null 예외 처리
		LookbookComment.validateField(lookbookCommentDto.getContent(), "Content");

		LookbookComment lookbookComment = lookbookCommentRepository.findById(commentId)
			.orElseThrow(
				() -> new IllegalArgumentException("comment id:" + commentId + " not found"));

		lookbookComment.updateComment(lookbookCommentDto);
	}

	/**
	 * 룩북 게시판 글의 댓글 삭제하기 API
	 *
	 * @param commentId 룩북 게시판 글의 댓글 id를 컨트롤러에서 받아옴.
	 */
	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Lookbook", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void deleteLookbookComment(Long commentId) {
		LookbookComment lookbookComment = lookbookCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));
		lookbookComment.deleteComment();
	}

	/**
	 * 주어진 게시글 ID의 작성자가 현재 사용자인지 확인
	 */
	@Override
	public boolean isPostAuthor(Long postId, String memberEmail) {
		return lookbookRepository.findById(postId)
			.map(lookbook -> lookbook.isAuthor(memberEmail))
			.orElse(false);
	}

	/**
	 * 주어진 댓글 ID의 작성자가 현재 사용자인지 확인
	 */
	@Override
	public boolean isCommentAuthor(Long commentId, String memberEmail) {
		return lookbookCommentRepository.findById(commentId)
			.map(lookbook -> lookbook.isAuthor(memberEmail))
			.orElse(false);
	}
}