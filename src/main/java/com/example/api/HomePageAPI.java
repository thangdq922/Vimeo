package com.example.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.UserDTO;
import com.example.dto.VideoDTO;
import com.example.entity.UserEntity;
import com.example.service.CommentService;
import com.example.service.UserService;
import com.example.service.VideoService;

import jakarta.validation.Valid;

@RestController
public class HomePageAPI {

	@Autowired
	UserService userService;

	@Autowired
	VideoService videoService;

	@Autowired
	CommentService commentService;

	@PostMapping(value = "/api/registration", consumes = { "multipart/form-data" })
	public ResponseEntity<?> registration(@RequestPart("user") @Valid UserDTO user, BindingResult result, Model model,
			 @RequestPart MultipartFile upAvatar) {
		UserEntity existing = userService.findByUserName(user.getUserName());
		if (existing != null) {			
			return ResponseEntity.badRequest().body("Username already exists");
		}
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

	@PutMapping("/api/view")
	public void setView(@RequestBody VideoDTO videoDTO) {
		videoService.setView(videoDTO);
	}

}
