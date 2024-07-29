package com.example.fashionlog.service;

import com.example.fashionlog.domain.Lookbook;
import com.example.fashionlog.domain.LookbookComment;
import com.example.fashionlog.dto.LookbookCommentDto;
import com.example.fashionlog.dto.LookbookDto;
import com.example.fashionlog.repository.LookbookCommentRepository;
import com.example.fashionlog.repository.LookbookRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LookbookService {

	private final LookbookRepository lookbookRepository;
	private final LookbookCommentRepository lookbookCommentRepository;

	@Autowired
	public LookbookService(LookbookRepository lookbookRepository, LookbookCommentRepository lookbookCommentRepository) {
		this.lookbookRepository = lookbookRepository;
		this.lookbookCommentRepository = lookbookCommentRepository;
	}

	public List<LookbookDto> getAllLookbooks() {
		return lookbookRepository.findByDeletedAtIsNull().stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	public LookbookDto getLookbookById(Long id) {
		Lookbook lookbook = lookbookRepository.findByIdAndDeletedAtIsNull(id);
		return convertToDto(lookbook);
	}

	public LookbookDto createLookbook(LookbookDto lookbookDto) {
		Lookbook lookbook = convertToEntity(lookbookDto);
		lookbook.setCreatedAt(LocalDateTime.now());
		Lookbook savedLookbook = lookbookRepository.save(lookbook);
		return convertToDto(savedLookbook);
	}

	public LookbookDto updateLookbook(Long id, LookbookDto lookbookDto) {
		Lookbook lookbook = lookbookRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Lookbook not found"));
		updateLookbookFromDto(lookbook, lookbookDto);
		lookbook.setUpdatedAt(LocalDateTime.now());
		Lookbook updatedLookbook = lookbookRepository.save(lookbook);
		return convertToDto(updatedLookbook);
	}

	public void deleteLookbook(Long id) {
		Lookbook lookbook = lookbookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid lookbook ID: " + id));
		lookbook.setDeletedAt(LocalDateTime.now());
		lookbookRepository.save(lookbook);
	}

	private LookbookDto convertToDto(Lookbook lookbook) {
		LookbookDto dto = new LookbookDto();
		dto.setId(lookbook.getId());
		dto.setMemberId(lookbook.getMemberId());
		dto.setTitle(lookbook.getTitle());
		dto.setContent(lookbook.getContent());
		dto.setPostStatus(lookbook.isPostStatus());
		dto.setCreatedAt(lookbook.getCreatedAt());
		dto.setUpdatedAt(lookbook.getUpdatedAt());
		return dto;
	}

	private Lookbook convertToEntity(LookbookDto dto) {
		Lookbook lookbook = new Lookbook();
		lookbook.setMemberId(dto.getMemberId());
		lookbook.setTitle(dto.getTitle());
		lookbook.setContent(dto.getContent());
		lookbook.setPostStatus(dto.isPostStatus());
		return lookbook;
	}

	private void updateLookbookFromDto(Lookbook lookbook, LookbookDto dto) {
		lookbook.setMemberId(dto.getMemberId());
		lookbook.setTitle(dto.getTitle());
		lookbook.setContent(dto.getContent());
		lookbook.setPostStatus(dto.isPostStatus());
	}

	public List<LookbookCommentDto> getCommentsByLookbookId(Long lookbookId) {
		return lookbookCommentRepository.findByLookbookId(lookbookId).stream()
			.filter(comment -> comment.getDeletedAt() == null)
			.map(this::convertCommentToDto)
			.collect(Collectors.toList());
	}

	public LookbookCommentDto createComment(LookbookCommentDto commentDto) {
		Lookbook lookbook = lookbookRepository.findById(commentDto.getLookbookId())
			.orElseThrow(() -> new RuntimeException("Lookbook not found"));
		LookbookComment comment = convertCommentToEntity(commentDto);
		comment.setLookbook(lookbook);
		comment.setCreatedAt(LocalDateTime.now());
		LookbookComment savedComment = lookbookCommentRepository.save(comment);
		return convertCommentToDto(savedComment);
	}

	public LookbookCommentDto updateComment(Long id, LookbookCommentDto commentDto) {
		LookbookComment comment = lookbookCommentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Comment not found"));
		if (comment.getDeletedAt() != null) {
			throw new RuntimeException("해당 댓글은 삭제되었습니다.");
		}
		comment.setContent(commentDto.getContent());
		LookbookComment updatedComment = lookbookCommentRepository.save(comment);
		return convertCommentToDto(updatedComment);
	}

	public void deleteComment(Long id) {
		LookbookComment comment = lookbookCommentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Comment not found"));
		comment.setDeletedAt(LocalDateTime.now());
		lookbookCommentRepository.save(comment);
	}

	private LookbookCommentDto convertCommentToDto(LookbookComment comment) {
		LookbookCommentDto dto = new LookbookCommentDto();
		dto.setId(comment.getId());
		dto.setLookbookId(comment.getLookbook().getId());
		dto.setMemberId(comment.getMemberId());
		dto.setContent(comment.getContent());
		dto.setCreatedAt(comment.getCreatedAt());
		dto.setUpdatedAt(comment.getUpdatedAt());
		return dto;
	}

	private LookbookComment convertCommentToEntity(LookbookCommentDto dto) {
		LookbookComment comment = new LookbookComment();
		comment.setMemberId(dto.getMemberId());
		comment.setContent(dto.getContent());
		comment.setCommentStatus(dto.getCommentStatus() != null ? dto.getCommentStatus() : true); // 기본값으로 true 설정
		return comment;
	}

	public LookbookCommentDto getCommentById(Long id) {
		LookbookComment comment = lookbookCommentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Comment not found"));
		return convertCommentToDto(comment);
	}

}