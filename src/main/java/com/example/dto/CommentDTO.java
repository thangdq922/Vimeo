package com.example.dto;

import java.sql.Date;

import com.example.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;

public class CommentDTO {
	private Long Id;
	
	@NotBlank(message = " is  empty")
	private String content;
	
	private Date createdDate;
	
	private UserEntity user;
	private Long video_id;
	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getVideo_id() {
		return video_id;
	}

	public void setVideo_id(Long video_id) {
		this.video_id = video_id;
	}

	
}
