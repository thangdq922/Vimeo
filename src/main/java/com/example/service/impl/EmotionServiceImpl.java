package com.example.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.converter.EmotionConverter;
import com.example.dto.EmotionDTO;
import com.example.entity.EmotionEntity;
import com.example.entity.VideoEntity;
import com.example.repository.EmotionRepository;
import com.example.repository.UserRepository;
import com.example.repository.VideoRepository;
import com.example.service.EmotionService;

import jakarta.transaction.Transactional;

@Service
public class EmotionServiceImpl implements EmotionService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	VideoRepository videoRepository;

	@Autowired
	EmotionRepository emotionRepository;

	@Autowired
	EmotionConverter converter;

	@Override
	public long countByEmotion(String emotion, Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		return emotionRepository.countByEmotionsAndVideo(emotion, videoEntity);
	}

	@Override
	@Transactional
	public void setEmotion(EmotionDTO dto) {
		VideoEntity videoEntity = videoRepository.findById(dto.getVideo_id()).get();
		EmotionEntity old = emotionRepository.findByVideoAndCreatedBy(videoEntity, dto.getUser_id());
		EmotionEntity entity = new EmotionEntity();
		if (old != null) {
			entity = old;
		} else {
			entity.setVideo(videoEntity);
		}
		entity.setEmotions(dto.getEmotions());
		emotionRepository.save(entity);
	}

	@Override
	@Transactional
	public void deleteEmotion(EmotionDTO dto) {
		VideoEntity videoEntity = videoRepository.findById(dto.getVideo_id()).get();
		EmotionEntity old = emotionRepository.findByVideoAndCreatedBy(videoEntity, dto.getUser_id());
		emotionRepository.delete(old);
	}

	@Override
	public List<EmotionDTO> findByVideo(Long id) {
		VideoEntity videoEntity = videoRepository.findById(id).get();
		List<EmotionEntity> entities = emotionRepository.findByVideo(videoEntity);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

}
