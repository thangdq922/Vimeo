package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.converter.CommentConverter;
import com.example.dto.CommentDTO;
import com.example.entity.CommentEntity;
import com.example.entity.VideoEntity;
import com.example.repository.CommentRepository;
import com.example.repository.UserRepository;
import com.example.repository.VideoRepository;
import com.example.service.CommentService;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImp implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	VideoRepository videoRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentConverter converter;

	@Override
	@Transactional
	public CommentDTO saveComment(CommentDTO commentDTO) {
		VideoEntity videoEntity = videoRepository.findById(commentDTO.getVideo_id()).get();
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setVideo(videoEntity);
		commentEntity.setContent(commentDTO.getContent());
		return converter.convertToDto(commentRepository.save(commentEntity));

	}

	@Override
	public List<CommentDTO> findByComment(String content) {
		List<CommentEntity> entities = commentRepository.findByContentContaining(content);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public List<CommentDTO> findByVideo(Long id) {
		Optional<VideoEntity> video = videoRepository.findById(id);
		List<CommentEntity> entities = commentRepository.findByVideo(video.get());
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public List<CommentDTO> findAllComments() {
		List<CommentEntity> entities = commentRepository.findAll();
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public void deleteComment(long[] ids) {
		for (long id : ids) {
			commentRepository.deleteById(id);
		}

	}

}
