package com.example.converter;

import org.springframework.stereotype.Component;

import com.example.dto.EmotionDTO;
import com.example.entity.EmotionEntity;

@Component
public class EmotionConverter {

	public EmotionDTO convertToDto(EmotionEntity entity) {
		EmotionDTO dto = new EmotionDTO();
		dto.setId(entity.getId());
		dto.setUser_id(entity.getCreateBy());
		dto.setVideo_id(entity.getVideo().getId());
		dto.setEmotions(entity.getEmotions());
		return dto;
	}
}
