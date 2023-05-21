package com.example.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.dto.VideoDTO;

public interface VideoService {
	
	VideoDTO saveVideo(VideoDTO videoDTO,  MultipartFile upFile,  MultipartFile upAvatar);
	
	VideoDTO findById(Long id);

	List<VideoDTO> searchVdieo(String keyWord);
	
	List<VideoDTO> findAllVideo();
	
	List<VideoDTO> findVideoByUser(Long id);

	void deleteVideo(long[] ids);
	
	void setView(VideoDTO dto);
	
	List<VideoDTO> sortView(List<VideoDTO> dtos);
	
	List<VideoDTO> sortDate(List<VideoDTO> dtos);
}
