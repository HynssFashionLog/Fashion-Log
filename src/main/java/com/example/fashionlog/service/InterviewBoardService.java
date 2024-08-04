package com.example.fashionlog.service;

import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.domain.InterviewBoardComment;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.dto.InterviewBoardCommentDto;
import com.example.fashionlog.dto.InterviewBoardDto;
import com.example.fashionlog.repository.InterviewBoardCommentRepository;
import com.example.fashionlog.repository.InterviewBoardRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InterviewBoardService {

	private final InterviewBoardRepository interviewBoardRepository;
	private final InterviewBoardCommentRepository interviewBoardCommentRepository;
	private final CurrentUserProvider currentUserProvider;

	public List<InterviewBoardDto> getAllInterviewPosts() {
		List<InterviewBoard> interviewPostList = interviewBoardRepository.findAllByStatusIsTrue();
		return interviewPostList.stream().map(InterviewBoardDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void createInterviewPost(InterviewBoardDto interviewBoardDto) {
		Member currentUser = currentUserProvider.getCurrentUser();

		interviewBoardDto.setCreatedAt(LocalDateTime.now());
		interviewBoardDto.setStatus(true);
		interviewBoardDto.setAuthorName(currentUser.getNickname());

		InterviewBoard interviewBoard = InterviewBoardDto.toEntity(interviewBoardDto, currentUser);
		interviewBoardRepository.save(interviewBoard);
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
		interviewBoard.update(interviewBoardDto);
	}

	@Transactional
	public void deleteInterviewPost(Long id) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		interviewBoard.delete();
	}

	public List<InterviewBoardCommentDto> getCommentList(Long id) {
		List<InterviewBoardComment> interviewBoardCommentList =
			interviewBoardCommentRepository.findAllByCommentStatusIsTrueAndInterviewBoardId(id);
		return interviewBoardCommentList.stream().map(InterviewBoardCommentDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void addComment(Long id, InterviewBoardCommentDto interviewBoardCommentDto) {
		Member currentUser = currentUserProvider.getCurrentUser();
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));

		interviewBoardCommentDto.setId(null);
		interviewBoardCommentDto.setBoardId(id);
		interviewBoardCommentDto.setCreatedAt(LocalDateTime.now());
		interviewBoardCommentDto.setCommentStatus(true);

		InterviewBoardComment interviewBoardComment = interviewBoardCommentDto.toEntity(
			interviewBoardCommentDto, interviewBoard, currentUser);
		interviewBoardCommentRepository.save(interviewBoardComment);
	}

	@Transactional
	public void updateInterviewComment(Long postId, Long commentId,
		InterviewBoardCommentDto interviewBoardCommentDto) {
		InterviewBoardComment interviewBoardComment = interviewBoardCommentRepository.findById(
				commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		interviewBoardComment.updateComment(interviewBoardCommentDto);
	}

	@Transactional
	public void deleteInterviewBoardComment(Long commentId) {
		InterviewBoardComment interviewBoardComment = interviewBoardCommentRepository.findById(
				commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		interviewBoardComment.deleteComment();
	}

}

