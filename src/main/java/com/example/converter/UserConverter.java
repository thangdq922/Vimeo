package com.example.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.dto.UserDTO;
import com.example.entity.UserEntity;

@Component
public class UserConverter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDTO convertToDto(UserEntity user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setUserName(user.getUserName());
		userDto.setFullName(user.getFullName());
		userDto.setAvatar(user.getAvatar());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	public UserEntity convertToEntity(UserDTO userDto) {
		UserEntity user = new UserEntity();
		user.setUserName(userDto.getUserName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		user.setAvatar(userDto.getAvatar());
		user.setFullName(userDto.getFullName());
		return user;

	}

	public UserEntity convertToEntity(UserEntity user, UserDTO userDto) {
		user.setUserName(userDto.getUserName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		user.setAvatar(userDto.getAvatar());
		user.setFullName(userDto.getFullName());
		return user;

	}
}
