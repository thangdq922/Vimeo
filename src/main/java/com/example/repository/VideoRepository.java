package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
	List<VideoEntity> findByTitleContaining(String title);
	
	List<VideoEntity> findByCreatedBy(Long id);
	
	List<VideoEntity> findByViews(Long views);
	
	List<VideoEntity> findByCreatedDate(Date createdDate);
	
 
	 
}
