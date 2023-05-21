package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CommentDTO;
import com.example.dto.UserDTO;
import com.example.dto.VideoDTO;
import com.example.service.CommentService;
import com.example.service.UserService;
import com.example.service.VideoService;

@Controller
@RequestMapping("/admin/")
public class Admin {
	@Autowired
	UserService userService;
	@Autowired
	VideoService videoService;
	@Autowired
	CommentService commentService;

	@GetMapping("home")
	public String AdminHome() {

		return "admin/home";
	}

	@GetMapping("videos")
	public String listAllVideo(Model model,
			@RequestParam(value = "search_query", required = false) String search_query) {
		model.addAttribute("result", "/admin/videos");
		List<VideoDTO> results = videoService.searchVdieo(search_query);
		if (search_query == null) {
			model.addAttribute("videos", videoService.findAllVideo());
		} else {
			model.addAttribute("videos", results);
		}
		return "admin/videos/videos";
	}

	@GetMapping("videos/edit")
	public String editVideo(@RequestParam(value = "id", required = false) Long id, Model model) {
		model.addAttribute("result", "/admin/videos");
		model.addAttribute("videos", videoService.findAllVideo());
		VideoDTO videoDTO = new VideoDTO();
		if (id != null) {
			videoDTO = videoService.findById(id);
		}
		model.addAttribute("video", videoDTO);
		return "admin/videos/editVideos";
	}

	@GetMapping("users")
	public String listUser(Model model, @RequestParam(value = "search_query", required = false) String search_query) {
		model.addAttribute("result", "/admin/users");
		List<UserDTO> results = userService.searchUser(search_query);
		if (search_query == null) {
			model.addAttribute("users", userService.findAllUsers());
		} else {
			model.addAttribute("users", results);
		}
		return "admin/users/users";
	}

	@GetMapping("users/edit")
	public String editUser(@RequestParam(value = "id", required = false) Long id, Model model) {
		model.addAttribute("result", "/admin/users");
		model.addAttribute("users", userService.findAllUsers());
		UserDTO user = new UserDTO();
		if (id != null) {
			user = userService.findById(id);
		}
		model.addAttribute("user", user);
		return "admin/users/editUser";
	}

	@GetMapping("comments")
	public String listComment(Model model,
			@RequestParam(value = "search_query", required = false) String search_query) {
		model.addAttribute("result", "/admin/comments");
		List<CommentDTO> results = commentService.findByComment(search_query);
		if (search_query == null) {
			model.addAttribute("comments", commentService.findAllComments());
		} else {
			model.addAttribute("comments", results);
		}
		return "admin/comments/comments";
	}

}
