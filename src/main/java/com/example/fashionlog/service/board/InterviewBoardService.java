package com.example.fashionlog.service.board;

import com.example.fashionlog.aop.AuthCheck;
import com.example.fashionlog.aop.AuthorType;
import com.example.fashionlog.domain.board.InterviewBoard;
import com.example.fashionlog.domain.comment.InterviewBoardComment;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.dto.comment.InterviewBoardCommentDto;
import com.example.fashionlog.dto.board.InterviewBoardDto;
import com.example.fashionlog.repository.comment.InterviewBoardCommentRepository;
import com.example.fashionlog.repository.board.InterviewBoardRepository;
import com.example.fashionlog.security.CurrentUserProvider;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InterviewBoardService implements BoardService {

	private final InterviewBoardRepository interviewBoardRepository;
	private final InterviewBoardCommentRepository interviewBoardCommentRepository;
	private final CurrentUserProvider currentUserProvider;

	public List<InterviewBoardDto> getAllInterviewPosts() {
		List<InterviewBoard> interviewPostList = interviewBoardRepository.findAllByStatusIsTrue();
		return interviewPostList.stream().map(InterviewBoardDto::fromEntity)
			.collect(Collectors.toList());
	}

	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Interview", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void createInterviewPost(InterviewBoardDto interviewBoardDto) {
		Member currentUser = currentUserProvider.getCurrentUser();

		interviewBoardDto.setCreatedAt(LocalDateTime.now());
		interviewBoardDto.setStatus(true);
		interviewBoardDto.setAuthorName(currentUser.getNickname());
		interviewBoardDto.setAuthorEmail(currentUser.getEmail());

		InterviewBoard interviewBoard = InterviewBoardDto.toEntity(interviewBoardDto, currentUser);
		interviewBoardRepository.save(interviewBoard);
	}


	public InterviewBoardDto getInterviewPostDetail(Long id) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		return InterviewBoardDto.fromEntity(interviewBoard);
	}

	@AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "Interview", AUTHOR_TYPE = AuthorType.POST)
	@Transactional
	public void updateInterviewPost(Long id, InterviewBoardDto interviewBoardDto) {
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));
		interviewBoard.update(interviewBoardDto);
	}

	@AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "Interview", AUTHOR_TYPE = AuthorType.POST)
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

	@AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "Interview", AUTHOR_TYPE = AuthorType.COMMENT)
	@Transactional
	public void addComment(Long id, InterviewBoardCommentDto interviewBoardCommentDto) {
		Member currentUser = currentUserProvider.getCurrentUser();
		InterviewBoard interviewBoard = interviewBoardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시판 정보를 찾을 수 없습니다."));

		interviewBoardCommentDto.setId(null);
		interviewBoardCommentDto.setBoardId(id);
		interviewBoardCommentDto.setCreatedAt(LocalDateTime.now());
		interviewBoardCommentDto.setCommentStatus(true);
		interviewBoardCommentDto.setAuthorEmail(currentUser.getEmail());

		InterviewBoardComment interviewBoardComment = interviewBoardCommentDto.toEntity(
			interviewBoardCommentDto, interviewBoard, currentUser);
		interviewBoardCommentRepository.save(interviewBoardComment);
	}

	@AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "Interview", AUTHOR_TYPE = AuthorType.COMMENT)
	@Transactional
	public void updateInterviewComment(Long postId, Long commentId,
		InterviewBoardCommentDto interviewBoardCommentDto) {
		InterviewBoardComment interviewBoardComment = interviewBoardCommentRepository.findById(
				commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		interviewBoardComment.updateComment(interviewBoardCommentDto);
	}

	@AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "Interview", AUTHOR_TYPE = AuthorType.COMMENT)
	@Transactional
	public void deleteInterviewBoardComment(Long commentId) {
		InterviewBoardComment interviewBoardComment = interviewBoardCommentRepository.findById(
				commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		interviewBoardComment.deleteComment();
	}

	/**
	 * 주어진 게시글 ID의 작성자가 현재 사용자인지 확인합니다.
	 *
	 * @param postId 확인할 게시글 ID
	 * @param memberEmail 현재 사용자의 이메일
	 * @return 현재 사용자가 게시글의 작성자인 경우 true, 그렇지 않은 경우 false
	 */
	@Override
	public boolean isPostAuthor(Long postId, String memberEmail) {
		return interviewBoardRepository.findById(postId)
			.map(interview -> interview.isAuthor(memberEmail))
			.orElse(false);
	}

	/**
	 * 주어진 댓글 ID의 작성자가 현재 사용자인지 확인합니다.
	 *
	 * @param commentId 확인할 게시글 ID
	 * @param memberEmail 현재 사용자의 이메일
	 * @return 현재 사용자가 게시글의 작성자인 경우 true, 그렇지 않은 경우 false
	 */
	@Override
	public boolean isCommentAuthor(Long commentId, String memberEmail) {
		return interviewBoardCommentRepository.findById(commentId)
			.map(interview -> interview.isAuthor(memberEmail))
			.orElse(false);
	}

}

