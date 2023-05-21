package com.example.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.CommentDTO;
import com.example.entity.CommentEntity;
import com.example.repository.UserRepository;

@Component
public class CommentConverter {

	@Autowired
	UserRepository userRepository;
	
	public CommentDTO convertToDto(CommentEntity commentEntity) {
		CommentDTO dto = new CommentDTO();
		dto.setId(commentEntity.getId());
		dto.setContent(commentEntity.getContent());
		dto.setUser(userRepository.findById(commentEntity.getCreateBy()).get());
		dto.setVideo_id(commentEntity.getVideo().getId());
		dto.setCreatedDate(new java.sql.Date(commentEntity.getCreatedDate().getTime()));
		return dto;
	}
}
