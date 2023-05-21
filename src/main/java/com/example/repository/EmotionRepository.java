package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.EmotionEntity;
import com.example.entity.VideoEntity;

public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {
	long countByEmotionsAndVideo(String emotion,VideoEntity videoEntity);
	
	EmotionEntity findByVideoAndCreatedBy(VideoEntity video, Long id);
	
	List<EmotionEntity> findByVideo(VideoEntity video);
}
