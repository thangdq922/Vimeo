package com.example.service;

import java.util.List;

import com.example.dto.EmotionDTO;

public interface EmotionService {
	long countByEmotion(String emotion, Long id);
	
	void setEmotion(EmotionDTO dto);
	
	void deleteEmotion(EmotionDTO dto);
	
	List<EmotionDTO> findByVideo(Long id);
}
