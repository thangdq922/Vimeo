package com.example.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.VideoDTO;
import com.example.entity.VideoEntity;
import com.example.repository.EmotionRepository;
import com.example.repository.UserRepository;

@Component
public class VideoConverter {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmotionRepository emotionRepository;

	public VideoDTO convertToDto(VideoEntity video) {
		VideoDTO videoDTO = new VideoDTO();
		videoDTO.setId(video.getId());
		videoDTO.setFile(video.getFile());
		videoDTO.setTitle(video.getTitle());
		videoDTO.setViews(video.getViews());
		videoDTO.setShortDescription(video.getShortDescription());
		videoDTO.setCreatedDate(new java.sql.Date(video.getCreatedDate().getTime()));
		videoDTO.setAvatar(video.getAvatar());
		videoDTO.setUser(userRepository.findById(video.getCreateBy()).get());
		videoDTO.setLikes(emotionRepository.countByEmotionsAndVideo("l", video));
		videoDTO.setDislike(emotionRepository.countByEmotionsAndVideo("d", video));
		return videoDTO;
	}

	public VideoEntity convertToEntity(VideoDTO dto) {
		VideoEntity entity = new VideoEntity();
		entity.setFile(dto.getFile());
		entity.setTitle(dto.getTitle());
		entity.setShortDescription(dto.getShortDescription());
		entity.setAvatar(dto.getAvatar());
		return entity;
	}

	public VideoEntity convertToEntity(VideoEntity entity, VideoDTO dto) {
		entity.setFile(dto.getFile());
		entity.setTitle(dto.getTitle());
		entity.setShortDescription(dto.getShortDescription());
		entity.setAvatar(dto.getAvatar());
		return entity;
	}
}
