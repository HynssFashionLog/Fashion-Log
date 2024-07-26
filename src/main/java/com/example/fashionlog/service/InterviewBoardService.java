package com.example.fashionlog.service;

import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.dto.InterviewBoardDto;
import com.example.fashionlog.repository.InterviewBoardRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InterviewBoardService {

	private final InterviewBoardRepository interviewBoardRepository;

	@Autowired
	public InterviewBoardService(InterviewBoardRepository interviewBoardRepository) {
		this.interviewBoardRepository = interviewBoardRepository;
	}

	public List<InterviewBoardDto> getAllInterviewPosts() {
		List<InterviewBoard> interviewPostList = interviewBoardRepository.findAll();
		return interviewPostList.stream().map(InterviewBoardDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void createInterviewPost(InterviewBoardDto interviewBoardDto) {
		interviewBoardDto.setCreatedAt(LocalDateTime.now());
		interviewBoardDto.setStatus(true);
		interviewBoardRepository.save(interviewBoardDto.toEntity());
	}


	public InterviewBoardDto getInterviewPostDetail(Long id) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		return InterviewBoardDto.fromEntity(interviewBoard);
	}

	@Transactional
	public void updateInterviewPost(Long id, InterviewBoardDto interviewBoardDto) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		interviewBoard.updateInterviewBoard(interviewBoardDto);
	}
}

