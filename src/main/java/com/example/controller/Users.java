package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.UserDTO;
import com.example.dto.VideoDTO;
import com.example.service.UserService;
import com.example.service.VideoService;
import com.example.util.SecurityUtil;

@Controller
public class Users {
	@Autowired
	VideoService videoService;
	@Autowired
	UserService userService;

	@GetMapping("/user/{id}/edit")
	public String editUser(@PathVariable Long id, Model model) {
		if (SecurityUtil.getPrincipal().getId() != id) {
			return "error/403";
		}
		UserDTO user = userService.findById(id);
		model.addAttribute("user", user);
		return "/user/editUser";

	}

	@GetMapping("/user/{id}/videos")
	public String listVideo(Model model, @PathVariable Long id,
			@RequestParam(value = "search_query", required = false) String search_query) {
		if (SecurityUtil.getPrincipal().getId() != id) {
			return "error/403";
		}
		List<VideoDTO> videos = videoService.findVideoByUser(id);
		model.addAttribute("searchs", videos);
		List<VideoDTO> results = videoService.searchVdieo(search_query);
		List<VideoDTO> resultsV = new ArrayList<VideoDTO>();
		for (VideoDTO rs : results) {
			for (VideoDTO v : videos) {
				if (v.getId() == rs.getId()) {
					resultsV.add(rs);
				}
			}
		}
		if (search_query == null) {
			model.addAttribute("videos", videos);
		} else {
			model.addAttribute("videos", resultsV);
		}
		return "user/videos";
	}

	@GetMapping("/user/{uid}/video/edit")
	public String editVideo(@RequestParam(value = "id", required = false) Long id, Model model,
			@PathVariable Long uid) {
		VideoDTO videoDTO = new VideoDTO();
		if (id != null) {
			videoDTO = videoService.findById(id);
		}
		if (SecurityUtil.getPrincipal().getId() != uid
				|| SecurityUtil.getPrincipal().getId() != videoDTO.getUser().getId()) {
			return "error/403";
		}

		model.addAttribute("searchs", videoService.findVideoByUser(uid));
		model.addAttribute("video", videoDTO);
		return "user/editVideos";
	}

}
