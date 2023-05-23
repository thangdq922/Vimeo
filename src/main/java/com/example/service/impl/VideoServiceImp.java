package com.example.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.converter.VideoConverter;
import com.example.dto.VideoDTO;
import com.example.entity.VideoEntity;
import com.example.repository.CommentRepository;
import com.example.repository.EmotionRepository;
import com.example.repository.UserRepository;
import com.example.repository.VideoRepository;
import com.example.service.VideoService;
import com.example.util.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class VideoServiceImp implements VideoService {

	@Autowired
	VideoRepository videoRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	EmotionRepository emotionRepository;
	@Autowired
	VideoConverter converter;
	@Value("${upload.path}" + "video\\")
	private String fileUpload;

	@Override
	@Transactional
	public VideoDTO saveVideo(VideoDTO videoDTO, MultipartFile upFile, MultipartFile upAvatar) {
		VideoEntity videoEntity = new VideoEntity();
		if (upAvatar != null) {
			String avatar = StringUtils.cleanPath(upAvatar.getOriginalFilename());
			videoDTO.setAvatar("/asset/private/video/" + SecurityUtil.getPrincipal().getId() + "_" + avatar);
			try {
				FileCopyUtils.copy(upAvatar.getBytes(),
						new File(this.fileUpload + SecurityUtil.getPrincipal().getId() + "_" + avatar));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (upFile != null) {
			String file = StringUtils.cleanPath(upFile.getOriginalFilename());
			videoDTO.setFile("/asset/private/video/" + SecurityUtil.getPrincipal().getId() + "_" + file);
			try {
				FileCopyUtils.copy(upFile.getBytes(),
						new File(this.fileUpload + SecurityUtil.getPrincipal().getId() + "_" + file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (videoDTO.getId() != null) {
			Optional<VideoEntity> oldVideo = videoRepository.findById(videoDTO.getId());

			videoEntity = converter.convertToEntity(oldVideo.get(), videoDTO);
		} else {
			videoEntity = converter.convertToEntity(videoDTO);
		}
		return converter.convertToDto(videoRepository.save(videoEntity));
	}

	@Override
	public List<VideoDTO> searchVdieo(String keyWord) {
		List<VideoEntity> entities = videoRepository.findByTitleContaining(keyWord);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());

	}

	@Override
	public VideoDTO findById(Long id) {
		Optional<VideoEntity> videoEntity = videoRepository.findById(id);
		return converter.convertToDto(videoEntity.get());

	}

	@Override
	public List<VideoDTO> findAllVideo() {
		List<VideoEntity> entities = videoRepository.findAll();
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public List<VideoDTO> findVideoByUser(Long id) {
		List<VideoEntity> entities = videoRepository.findByCreatedBy(id);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteVideo(long[] ids) {
		for (long id : ids) {
			videoRepository.deleteById(id);
		}
	}

	@Override
	@Transactional
	public void setView(VideoDTO dto) {
		Optional<VideoEntity> oldVideo = videoRepository.findById(dto.getId());
		oldVideo.get().setViews(dto.getViews());
	}

//	Sort
	@Override
	public List<VideoDTO> sortView(List<VideoDTO> dtos) {
		List<Long> views = new ArrayList<Long>();
		dtos.forEach((dto) -> views.add(dto.getViews()));
		quickSort(views);
		List<VideoDTO> videoDTOs = new ArrayList<>();
		for (Long view : views) {
			for (VideoDTO dto : dtos) {
				if (view == dto.getViews() && videoDTOs.contains(dto) == false) {
					videoDTOs.add(dto);
				}
			}
		}
		return videoDTOs;
	}

	@Override
	public List<VideoDTO> sortDate(List<VideoDTO> dtos) {
		List<Date> dates = new ArrayList<Date>();
		dtos.forEach((dto) -> dates.add(dto.getCreatedDate()));
		dates.sort((a, b) -> b.compareTo(a));
		List<VideoDTO> videoDTOs = new ArrayList<>();
		for (Date date : dates) {
			for (VideoDTO dto : dtos) {
				if (date == dto.getCreatedDate() && videoDTOs.contains(dto) == false) {
					videoDTOs.add(dto);
				}
			}
		}
		return videoDTOs;
	}

// quicksort
	private void quickSort(List<Long> list) {
		sort(list, 0, list.size() - 1);
	}

	private void sort(List<Long> list, int from, int to) {
		if (from < to) {
			int pivot = from;
			int left = from + 1;
			int right = to;
			long pivotValue = list.get(pivot);
			while (left <= right) {
				while (left <= to && pivotValue <= list.get(left)) {
					left++;
				}
				while (right > from && pivotValue > list.get(right)) {
					right--;
				}
				if (left < right) {
					Collections.swap(list, left, right);
				}
			}
			Collections.swap(list, pivot, left - 1);
			sort(list, from, right - 1);
			sort(list, right + 1, to);
		}
	}
}
