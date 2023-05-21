package com.example.dto;

import java.util.Date;

public class EmotionDTO {

	private Long id;

	private String emotions;
	
	private Date createdDate;
	
	private Long user_id;
	
	private Long video_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmotions() {
		return emotions;
	}

	public void setEmotions(String emotions) {
		this.emotions = emotions;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getVideo_id() {
		return video_id;
	}

	public void setVideo_id(Long video_id) {
		this.video_id = video_id;
	}
	
}
