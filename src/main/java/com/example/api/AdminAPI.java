package com.example.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.UserDTO;
import com.example.dto.VideoDTO;
import com.example.service.CommentService;
import com.example.service.UserService;
import com.example.service.VideoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/api/")
public class AdminAPI {

	@Autowired
	VideoService videoService;
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;

	@PostMapping("video")
	public ResponseEntity<?> addVideo(@RequestPart VideoDTO video, @RequestPart(required = false) MultipartFile upFile,
			@RequestPart(required = false) MultipartFile upAvatar) {
		if (upFile == null || upAvatar == null) {
			return ResponseEntity.badRequest().body("file or avatar is empty");
		}
		return ResponseEntity.ok(videoService.saveVideo(video, upFile, upAvatar));
	}

	@DeleteMapping("video")
	public void deleteVideo(@RequestBody long[] ids) {
		videoService.deleteVideo(ids);
		;
	}

	@PutMapping("video")
	public VideoDTO updateVideo(@RequestPart VideoDTO video, @RequestPart(required = false) MultipartFile upFile,
			@RequestPart(required = false) MultipartFile upAvatar) {
		return videoService.saveVideo(video, upFile, upAvatar);
	}

	@PostMapping("user")
	public ResponseEntity<?> addUser(@RequestPart(required = false) @Valid UserDTO user, BindingResult result,
			Model model, @RequestPart(required = false) MultipartFile upAvatar) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			Map<String, String> errors = new HashMap<>();
			result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			String errorMsg = "";
			for (String key : errors.keySet()) {
				errorMsg += key + ": " + errors.get(key) + "\n";
			}
			return ResponseEntity.badRequest().body(errorMsg);
		}
		userService.saveUser(user, upAvatar);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("user")
	public void deleteUser(@RequestBody long[] ids) {
		userService.deleteUsers(ids);
	}

	@PutMapping("user")
	public ResponseEntity<?> updateUser(@RequestPart(required = false) @Valid UserDTO user, BindingResult result,
			Model model, @RequestPart(required = false) MultipartFile upAvatar) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			Map<String, String> errors = new HashMap<>();
			result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			String errorMsg = "";
			for (String key : errors.keySet()) {
				errorMsg += key + ": " + errors.get(key) + "\n";
			}
			return ResponseEntity.badRequest().body(errorMsg);
		}
		userService.saveUser(user, upAvatar);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("comment")
	public void deleteComment(@RequestBody long[] ids) {
		commentService.deleteComment(ids);
	}

}
