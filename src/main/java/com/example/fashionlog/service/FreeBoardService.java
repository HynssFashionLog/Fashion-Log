package com.example.fashionlog.service;

import com.example.fashionlog.domain.FreeBoard;
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

	public List<FreeBoardDto> getAllFreeBoards() {
		return freeBoardRepository.findAll().stream().filter(FreeBoard::getPostStatus)
			.map(FreeBoardDto::convertToDto)
			.collect(Collectors.toList());
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
	public void deleteFreeBoardPost(Long id) {
		FreeBoard freeBoard = freeBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("id: " + id + " not found"));
		freeBoardRepository.delete(freeBoard);
	}

	public List<FreeBoardCommentDto> getCommentsByFreeBoardId(Long freeBoardId) {
		return freeBoardCommentRepository.findByFreeBoardId(freeBoardId).stream()
			.map(FreeBoardCommentDto::convertToDto)
			.collect(Collectors.toList());
	}
}