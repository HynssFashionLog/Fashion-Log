package com.example.fashionlog.service;

import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.dto.FreeBoardDto;
import com.example.fashionlog.repository.FreeBoardCommentRepository;
import com.example.fashionlog.repository.FreeBoardRepository;
import java.util.List;
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
}