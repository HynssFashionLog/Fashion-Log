package com.example.fashionlog.service;

import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.dto.InterviewBoardDto;
import com.example.fashionlog.repository.InterviewBoardRepository;
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
		return interviewPostList.stream().map(InterviewBoardDto::fromEntity).collect(Collectors.toList());
	}
}

