package com.example.fashionlog.service;

import com.example.fashionlog.domain.Lookbook;
import com.example.fashionlog.dto.LookbookDto;
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

	@Autowired
	public LookbookService(LookbookRepository lookbookRepository) {
		this.lookbookRepository = lookbookRepository;
	}

	public List<LookbookDto> getAllLookbooks() {
		return lookbookRepository.findAll()
			.stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	public LookbookDto getLookbookById(Long id) {
		Lookbook lookbook = lookbookRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));
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
		lookbookRepository.deleteById(id);
	}

	private LookbookDto convertToDto(Lookbook lookbook) {
		LookbookDto dto = new LookbookDto();
		dto.setId(lookbook.getId());
		dto.setMemberId(lookbook.getMemberId());
		dto.setTitle(lookbook.getTitle());
		dto.setContent(lookbook.getContent());
		dto.setPostStatus(lookbook.isPostStatus());
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

}