package com.example.fashionlog.service;

import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.domain.FreeBoardComment;
import com.example.fashionlog.dto.FreeBoardCommentDto;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.repository.FreeBoardCommentRepository;
import com.example.fashionlog.repository.FreeBoardRepository;
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
			.map(FreeBoardDto::convertToDto)
			.collect(Collectors.toList());
		return freeBoards.isEmpty() ? Optional.empty() : Optional.of(freeBoards);
	}

	@Transactional
	public void createFreeBoardPost(FreeBoardDto freeBoardDto) {
		freeBoardRepository.save(FreeBoardDto.convertToEntity(freeBoardDto));
	}

	public Optional<FreeBoardDto> getFreeBoardDtoById(Long id) {
		return freeBoardRepository.findById(id)
			.map(FreeBoardDto::convertToDto);
	}

	@Transactional
	public void updateFreeBoardPost(Long id, FreeBoardDto freeBoardDto) {
		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoard.updateFreeBoard(freeBoardDto);
		freeBoardRepository.save(freeBoard);
	}

	@Transactional
	public void deleteFreeBoardPost(Long id, FreeBoardDto freeBoardDto) {
		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoard.deleteFreeBoard(freeBoardDto);
		freeBoardRepository.save(freeBoard);
	}

	public Optional<List<FreeBoardCommentDto>> getCommentsByFreeBoardId(Long freeBoardId) {
		List<FreeBoardCommentDto> comments = freeBoardCommentRepository.findByFreeBoardId(
				freeBoardId).stream()
			.filter(FreeBoardComment::getCommentStatus)
			.map(FreeBoardCommentDto::convertToDto)
			.collect(Collectors.toList());
		return comments.isEmpty() ? Optional.empty() : Optional.of(comments);
	}

	@Transactional
	public void createFreeBoardComment(Long id, FreeBoardCommentDto freeBoardCommentDto) {
		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		FreeBoardComment freeBoardComment = FreeBoardCommentDto.convertToEntity(freeBoard,
			freeBoardCommentDto);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	@Transactional
	public void updateFreeBoardComment(Long postId, Long commentId,
		FreeBoardCommentDto freeBoardCommentDto) {
		FreeBoard freeBoard = freeBoardRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + postId + " not found"));
		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(
				() -> new IllegalArgumentException("comment id:" + commentId + " not found"));
		freeBoardComment.updateFreeBoardComment(freeBoardCommentDto, freeBoard);
		freeBoardCommentRepository.save(freeBoardComment);
	}

	@Transactional
	public void deleteFreeBoardComment(Long postId, Long commentId,
		FreeBoardCommentDto freeBoardCommentDto) {
		FreeBoard freeBoard = freeBoardRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + postId + " not found"));
		FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("id: " + commentId + " not found"));
		freeBoardComment.deleteFreeBoardComment(freeBoardCommentDto, freeBoard);
		freeBoardCommentRepository.save(freeBoardComment);
	}
}