package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emotion")
public class EmotionEntity extends BaseEntity {

	private String emotions;

	@ManyToOne
	@JoinColumn(name = "video_id")
	private VideoEntity video;

	public String getEmotions() {
		return emotions;
	}

	public void setEmotions(String emotions) {
		this.emotions = emotions;
	}

	public VideoEntity getVideo() {
		return video;
	}

	public void setVideo(VideoEntity video) {
		this.video = video;
	}
	
}
