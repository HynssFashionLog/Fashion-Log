package com.example.fashionlog.service;

import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.domain.FreeBoardComment;
import com.example.fashionlog.dto.FreeBoardCommentDto;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.repository.FreeBoardCommentRepository;
import com.example.fashionlog.repository.FreeBoardRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 자유 게시판 Service -> FreeBoard API 비즈니스 로직 구현.
 *
 * @author Hynss
 * @version 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class FreeBoardService {

	private final FreeBoardRepository freeBoardRepository;
	private final FreeBoardCommentRepository freeBoardCommentRepository;

	@Autowired
	public FreeBoardService(FreeBoardRepository freeBoardRepository,
		FreeBoardCommentRepository freeBoardCommentRepository) {
		this.freeBoardRepository = freeBoardRepository;
		this.freeBoardCommentRepository = freeBoardCommentRepository;
	}

	/**
	 * 자유 게시판 글 목록 조회하기 API
	 *
	 * @return getPostStatus(글이 삭제되었는지, soft delete 방식)가 true일 경우 자유 게시판의 전체 글을 반환함.
	 */
	public Optional<List<FreeBoardDto>> getAllFreeBoards() {
		List<FreeBoardDto> freeBoards = freeBoardRepository.findAll().stream()
			.filter(FreeBoard::getPostStatus)
			.map(this::convertToDto)
			.collect(Collectors.toList());
		return freeBoards.isEmpty() ? Optional.empty() : Optional.of(freeBoards);
	}

	/**
	 * 자유 게시판 글 작성하기 API
	 *
	 * @param freeBoardDto 컨트롤러에서 DB에 삽입할 DTO를 받아옴.
	 */
	@Transactional
	public void createFreeBoardPost(FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		FreeBoard.validateField(freeBoardDto.getTitle(), "Title");
		FreeBoard.validateField(freeBoardDto.getContent(), "Content");

		freeBoardRepository.save(this.convertToEntity(freeBoardDto));
	}

	/**
	 * 자유 게시판 글 상세보기 API
	 *
	 * @param id 글 하나를 상세하게 보기위해 게시글 id를 받아옴.
	 * @return 자유 게시판 데이터베이스에서 id가 같은 게시글을 반환함.
	 */
	public Optional<FreeBoardDto> getFreeBoardDtoById(Long id) {
		return freeBoardRepository.findById(id)
			.map(this::convertToDto);
	}

	/**
	 * 자유 게시판 글 수정하기 API
	 *
	 * @param id           수정할 글의 id를 받아옴.
	 * @param freeBoardDto 컨트롤러에서 DB에 수정할 DTO를 받아옴.
	 */
	@Transactional
	public void updateFreeBoardPost(Long id, FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		FreeBoard.validateField(freeBoardDto.getTitle(), "Title");
		FreeBoard.validateField(freeBoardDto.getContent(), "Content");

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoard.updateFreeBoard(freeBoardDto);
		freeBoardRepository.save(freeBoard);
	}

	/**
	 * 자유 게시판 글 삭제하기 API
	 *
	 * @param id 삭제할 글의 id를 받아옴.
	 */
	@Transactional
	public void deleteFreeBoardPost(Long id) {
		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoard.deleteFreeBoard();
		freeBoardRepository.save(freeBoard);
	}

	/**
	 * 자유 게시판 글의 댓글 목록 조회하기 API
	 *
	 * @param freeBoardId 댓글 들이 속한 자유 게시판 글의 id를 받아옴.
	 * @return 자유 게시판에서 조회한 글에 해당하는 댓글 목록을 반환함.
	 */
	public Optional<List<FreeBoardCommentDto>> getCommentsByFreeBoardId(Long freeBoardId) {
		List<FreeBoardCommentDto> comments = freeBoardCommentRepository.findByFreeBoardId(
				freeBoardId).stream()
			.filter(FreeBoardComment::getCommentStatus)
			.map(this::convertToDto)
			.collect(Collectors.toList());
		return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
	}

	/**
	 * 자유 게시판 글의 댓글 작성하기 API
	 *
	 * @param id                  자유 게시판 글의 id를 컨트롤러에서 받아옴.
	 * @param freeBoardCommentDto 컨트롤러에서 DB에 삽입할 댓글 DTO를 받아옴.
	 */
	@Transactional
	public void createFreeBoardComment(Long id, FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		FreeBoardComment.validateField(freeBoardCommentDto.getContent(), "Content");

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		FreeBoardComment freeBoardComment = this.convertToEntity(freeBoard,
			freeBoardCommentDto);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	/**
	 * 자유 게시판 글의 댓글 수정하기 API
	 *
	 * @param commentId           자유 게시판 글의 댓글 id를 컨트롤러에서 받아옴.
	 * @param freeBoardCommentDto 컨트롤러에서 DB에 수정할 댓글 DTO를 받아옴.
	 */
	@Transactional
	public void updateFreeBoardComment(Long commentId,
		FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외 처리
		FreeBoardComment.validateField(freeBoardCommentDto.getContent(), "Content");

		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(
				() -> new IllegalArgumentException("comment id:" + commentId + " not found"));
		freeBoardComment.updateFreeBoardComment(freeBoardCommentDto);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	/**
	 * 자유 게시판 글의 댓글 삭제하기 API
	 *
	 * @param commentId 자유 게시판 글의 댓글 id를 컨트롤러에서 받아옴.
	 */
	@Transactional
	public void deleteFreeBoardComment(Long commentId) {
		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));
		freeBoardComment.deleteFreeBoardComment();
		freeBoardCommentRepository.save(freeBoardComment);
	}

	/**
	 * FreeBoard -> FreeBoardDto
	 */
	public FreeBoardDto convertToDto(FreeBoard freeBoard) {
		return FreeBoardDto.builder()
			.id(freeBoard.getId())
			.title(freeBoard.getTitle())
			.content(freeBoard.getContent())
			.postStatus(freeBoard.getPostStatus())
			.createdAt(freeBoard.getCreatedAt())
			.updatedAt(freeBoard.getUpdatedAt())
			.deletedAt(freeBoard.getDeletedAt())
			.build();
	}

	/**
	 * FreeBoardComment -> FreeBoardCommentDto
	 */
	public FreeBoardCommentDto convertToDto(FreeBoardComment freeBoardComment) {
		return FreeBoardCommentDto.builder()
			.id(freeBoardComment.getId())
			.freeBoardId(freeBoardComment.getFreeBoard().getId())
			.content(freeBoardComment.getContent())
			.commentStatus(freeBoardComment.getCommentStatus())
			.createdAt(freeBoardComment.getCreatedAt())
			.updatedAt(freeBoardComment.getUpdatedAt())
			.deletedAt(freeBoardComment.getDeletedAt())
			.build();
	}

	/**
	 * FreeBoardDto -> FreeBoard
	 */
	public FreeBoard convertToEntity(FreeBoardDto freeBoardDto) {
		return FreeBoard.builder()
			.title(freeBoardDto.getTitle())
			.content(freeBoardDto.getContent())
			.postStatus(freeBoardDto.getPostStatus() != null ? freeBoardDto.getPostStatus() : true)
			.createdAt(freeBoardDto.getCreatedAt() != null ? freeBoardDto.getCreatedAt()
				: LocalDateTime.now())
			.updatedAt(freeBoardDto.getUpdatedAt())
			.deletedAt(freeBoardDto.getDeletedAt())
			.build();
	}

	/**
	 * FreeBoardCommentDto -> FreeBoardComment
	 */
	public FreeBoardComment convertToEntity(FreeBoard findedFreeBoard,
		FreeBoardCommentDto freeBoardCommentDto) {
		return FreeBoardComment.builder()
			.freeBoard(findedFreeBoard)
			.content(freeBoardCommentDto.getContent())
			.commentStatus(freeBoardCommentDto.getCommentStatus() != null
				? freeBoardCommentDto.getCommentStatus() : true)
			.createdAt(
				freeBoardCommentDto.getCreatedAt() != null ? freeBoardCommentDto.getCreatedAt()
					: LocalDateTime.now())
			.updatedAt(freeBoardCommentDto.getUpdatedAt())
			.deletedAt(freeBoardCommentDto.getDeletedAt())
			.build();
	}
}