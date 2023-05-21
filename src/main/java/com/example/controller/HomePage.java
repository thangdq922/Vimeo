package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CommentDTO;
import com.example.dto.UserDTO;
import com.example.dto.VideoDTO;
import com.example.service.CommentService;
import com.example.service.EmotionService;
import com.example.service.UserService;
import com.example.service.VideoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomePage {

	@Autowired
	VideoService videoService;
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;
	@Autowired
	EmotionService emotionService;

	@GetMapping({ "/" })
	public String home(Model model) {
		model.addAttribute("videos", videoService.findAllVideo());
		model.addAttribute("users", userService.findAllUsers());
		return "anonymous/homepage";
	}

	@GetMapping("/watch")
	public String video(@RequestParam(value = "id", required = false) Long id, Model model) {
		model.addAttribute("videos", videoService.findAllVideo());
		model.addAttribute("users", userService.findAllUsers());
		if (id != null) {
			List<CommentDTO> comments = commentService.findByVideo(id);
			VideoDTO video = videoService.findById(id);
			model.addAttribute("comments", comments);
			model.addAttribute("emotions", emotionService.findByVideo(id));
			model.addAttribute("video", video);
			return "anonymous/watch";
		}
		return "anonymous/homepage";
	}

	@GetMapping("/results")
	public String searchVideo(@RequestParam(value = "search_query", required = false) String search_query,
			Model model) {
		model.addAttribute("title", search_query);
		model.addAttribute("videos", videoService.findAllVideo());
		model.addAttribute("users", userService.findAllUsers());

		List<VideoDTO> resultsV = videoService.searchVdieo(search_query);
		List<Long> ids = new ArrayList<Long>();
		resultsV.forEach((v) -> ids.add(v.getId()));
		List<UserDTO> resultsU = userService.searchUser(search_query);
		List<VideoDTO> resultsVU = new ArrayList<>();
		for (UserDTO u : resultsU) {
			List<VideoDTO> dtos = videoService.findVideoByUser(u.getId());
			for (VideoDTO v : dtos) {
				if (ids.contains(v.getId()) == false) {
					resultsVU.add(v);
				}
			}
		}
		resultsV.addAll(resultsVU);

		model.addAttribute("resultsV", resultsV);
		model.addAttribute("resultsV_view", videoService.sortView(resultsV));
		model.addAttribute("resultsV_date", videoService.sortDate(resultsV));

		return "anonymous/search";
	}

	@GetMapping("/login")
	public String loginForm(HttpServletRequest request, @RequestParam(required = false) boolean error, Model model) {
		String referrer = request.getHeader("Referer");
		if (referrer != null && referrer.equals("http://localhost:8080/login") == false) {
			request.getSession().setAttribute("url_prior_login", referrer);
		}
		if (error == true) {
			model.addAttribute("error", "Username or Password is valid");
		}

		return "anonymous/login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserDTO user = new UserDTO();
		model.addAttribute("user", user);
		return "anonymous/register";
	}

}
