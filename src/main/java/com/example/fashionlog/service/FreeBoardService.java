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

	public Optional<List<FreeBoardDto>> getAllFreeBoards() {
		List<FreeBoardDto> freeBoards = freeBoardRepository.findAll().stream()
			.filter(FreeBoard::getPostStatus)
			.map(this::convertToDto)
			.collect(Collectors.toList());
		return freeBoards.isEmpty() ? Optional.empty() : Optional.of(freeBoards);
	}

	@Transactional
	public void createFreeBoardPost(FreeBoardDto freeBoardDto) {
		// Not Null 예외처리 로직
		FreeBoard.validateField(freeBoardDto.getTitle(), "Title");
		FreeBoard.validateField(freeBoardDto.getContent(), "Content");

		freeBoardRepository.save(this.convertToEntity(freeBoardDto));
	}

	public Optional<FreeBoardDto> getFreeBoardDtoById(Long id) {
		return freeBoardRepository.findById(id)
			.map(this::convertToDto);
	}

	@Transactional
	public void updateFreeBoardPost(Long id, FreeBoardDto freeBoardDto) {
		// Not Null 예외처리 로직
		FreeBoard.validateField(freeBoardDto.getTitle(), "Title");
		FreeBoard.validateField(freeBoardDto.getContent(), "Content");

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoard.updateFreeBoard(freeBoardDto);
		freeBoardRepository.save(freeBoard);
	}

	@Transactional
	public void deleteFreeBoardPost(Long id) {
		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoard.deleteFreeBoard();
		freeBoardRepository.save(freeBoard);
	}

	public Optional<List<FreeBoardCommentDto>> getCommentsByFreeBoardId(Long freeBoardId) {
		List<FreeBoardCommentDto> comments = freeBoardCommentRepository.findByFreeBoardId(
				freeBoardId).stream()
			.filter(FreeBoardComment::getCommentStatus)
			.map(this::convertToDto)
			.collect(Collectors.toList());
		return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
	}

	@Transactional
	public void createFreeBoardComment(Long id, FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외처리 로직
		FreeBoardComment.validateField(freeBoardCommentDto.getContent(), "Content");

		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		FreeBoardComment freeBoardComment = this.convertToEntity(freeBoard,
			freeBoardCommentDto);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	@Transactional
	public void updateFreeBoardComment(Long commentId,
		FreeBoardCommentDto freeBoardCommentDto) {
		// Not Null 예외처리 로직
		FreeBoardComment.validateField(freeBoardCommentDto.getContent(), "Content");

		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(
				() -> new IllegalArgumentException("comment id:" + commentId + " not found"));
		freeBoardComment.updateFreeBoardComment(freeBoardCommentDto);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	@Transactional
	public void deleteFreeBoardComment(Long commentId) {
		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));
		freeBoardComment.deleteFreeBoardComment();
		freeBoardCommentRepository.save(freeBoardComment);
	}

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