package com.example.fashionlog.service;

import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.domain.InterviewBoardComment;
import com.example.fashionlog.dto.InterviewBoardCommentDto;
import com.example.fashionlog.dto.InterviewBoardDto;
import com.example.fashionlog.repository.InterviewBoardCommentRepository;
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
	private final InterviewBoardCommentRepository interviewBoardCommentRepository;

	@Autowired
	public InterviewBoardService(InterviewBoardRepository interviewBoardRepository,
		InterviewBoardCommentRepository interviewBoardCommentRepository) {
		this.interviewBoardRepository = interviewBoardRepository;
		this.interviewBoardCommentRepository = interviewBoardCommentRepository;
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

	@Transactional
	public void deleteInterviewPost(Long id) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		interviewBoardRepository.delete(interviewBoard);
	}

	public List<InterviewBoardCommentDto> getCommentList(Long id) {
		List<InterviewBoardComment> interviewBoardCommentList = interviewBoardCommentRepository.findByInterviewBoard_Id(id);
		return interviewBoardCommentList.stream().map(InterviewBoardCommentDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void addComment(Long id, InterviewBoardCommentDto interviewBoardCommentDto) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		interviewBoardCommentDto.setId(null);
		interviewBoardCommentDto.setBoardId(id);
		interviewBoardCommentDto.setCreatedAt(LocalDateTime.now());
		interviewBoardCommentDto.setStatus(true);
		InterviewBoardComment interviewBoardComment = interviewBoardCommentDto.toEntity(
			interviewBoardCommentDto, interviewBoard);
		interviewBoardCommentRepository.save(interviewBoardComment);
	}

	@Transactional
	public void updateInterviewComment(Long postId, Long commentId, InterviewBoardCommentDto interviewBoardCommentDto) {
		InterviewBoardComment interviewBoardComment = interviewBoardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		interviewBoardComment.updateInterviewComment(interviewBoardCommentDto);
	}

	@Transactional
	public void deleteInterviewBoardComment(Long commentId) {
		InterviewBoardComment interviewBoardComment = interviewBoardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		interviewBoardComment.deleteInterviewComment();
	}

}

