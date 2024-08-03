package com.example.fashionlog.service;

import com.example.fashionlog.domain.Category;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Notice;
import com.example.fashionlog.domain.NoticeComment;
import com.example.fashionlog.dto.NoticeCommentDto;
import com.example.fashionlog.dto.NoticeDto;
import com.example.fashionlog.repository.NoticeCommentRepository;
import com.example.fashionlog.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final NoticeCommentRepository noticeCommentRepository;
	private final CurrentUserProvider currentUserProvider;

	public Optional<List<NoticeDto>> getAllNotices() {
		List<NoticeDto> notices = noticeRepository.findAllByStatusIsTrueOrderByCreatedAtDesc()
			.stream()
			.map(NoticeDto::convertToDto)
			.collect(Collectors.toList());
		return notices.isEmpty() ? Optional.empty() : Optional.of(notices);
	}

	@Transactional
	public void createNotice(NoticeDto noticeDto) {
		Member currentUser = currentUserProvider.getCurrentUser();
		noticeDto.setStatus(true);
		Notice notice = NoticeDto.convertToEntity(noticeDto, currentUser);
		noticeRepository.save(notice);
	}

	public NoticeDto getNoticeDetail(Long id) {
		Notice notice = findByIdAndStatusIsTrue(id);
		return NoticeDto.convertToDto(notice);
	}

	@Transactional
	public void editNotice(Long id, NoticeDto noticeDto) {
		Notice notice = findByIdAndStatusIsTrue(id);
		notice.update(noticeDto);
	}

	@Transactional
	public void deleteNotice(Long id) {
		Notice notice = findByIdAndStatusIsTrue(id);
		notice.delete();
	}

	public List<NoticeCommentDto> getNoticeCommentList(Long id) {
		List<NoticeComment> noticeCommentDtoList
			= noticeCommentRepository.findAllByCommentStatusIsTrueAndNoticeId(id);
		return noticeCommentDtoList.stream().map(NoticeCommentDto::convertToDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public void createNoticeComment(Long id, NoticeCommentDto noticeCommentDto) {
		Notice notice = findByIdAndStatusIsTrue(id);
		Member currentUser = currentUserProvider.getCurrentUser();

		noticeCommentDto.setNoticeId(null);
		noticeCommentDto.setNoticeId(id);
		noticeCommentDto.setCommentStatus(true);

		NoticeComment noticeComment = NoticeCommentDto.convertToEntity(noticeCommentDto, notice, currentUser);
		noticeCommentRepository.save(noticeComment);
	}

	@Transactional
	public void editNoticeComment(Long postId, Long commentId,
		NoticeCommentDto noticeCommentDto) {
		findByIdAndStatusIsTrue(postId);
		NoticeComment noticeComment = findByIdAndCommentStatusIsTrue(commentId);
		noticeComment.updateComment(noticeCommentDto);
	}

	@Transactional
	public void deleteNoticeComment(Long commentId) {
		NoticeComment noticeComment = findByIdAndCommentStatusIsTrue(commentId);
		noticeComment.deleteComment();
	}

	public Optional<List<NoticeDto>> getNoticeListByDailyLook(Category category) {
		System.out.println(category);
		List<NoticeDto> getNotice = noticeRepository.findTop5ByCategoryAndStatusIsTrueOrderByCreatedAtDesc(category)
			.stream()
			.map(NoticeDto::convertToDto)
			.collect(Collectors.toList());
		System.out.println(getNotice);
		return getNotice.isEmpty() ? Optional.empty() : Optional.of(getNotice);
	}

	private Notice findByIdAndStatusIsTrue(Long id) {
		return noticeRepository.findByIdAndStatusIsTrue(id)
			.orElseThrow(() -> new EntityNotFoundException("공지사항을 찾지 못했습니다."));
	}

	private NoticeComment findByIdAndCommentStatusIsTrue(Long id) {
		return noticeCommentRepository.findByIdAndCommentStatusIsTrue(id)
			.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
	}
}
