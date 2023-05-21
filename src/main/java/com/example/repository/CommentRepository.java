package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.CommentEntity;
import com.example.entity.VideoEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findByContentContaining(String content);
	
	List<CommentEntity> findByVideo(VideoEntity video);
		
	
}
