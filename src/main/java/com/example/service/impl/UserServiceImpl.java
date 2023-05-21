package com.example.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.converter.UserConverter;
import com.example.dto.UserDTO;
import com.example.entity.RoleEntity;
import com.example.entity.UserEntity;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserConverter converter;

	@Override
	@Transactional
	public UserDTO saveUser(UserDTO userDto, MultipartFile upAvatar) {
		RoleEntity role = roleRepository.findByCode("ROLE_USER");
		UserEntity user = new UserEntity();

		if (userDto.getId() != null) {
			Optional<UserEntity> oldUser = userRepository.findById(userDto.getId());
			user = converter.convertToEntity(oldUser.get(), userDto);
		} else {
			user = converter.convertToEntity(userDto);
			user.setRoles(Arrays.asList(role));
		}
		if (upAvatar != null) {
			String fileName = StringUtils.cleanPath(upAvatar.getOriginalFilename());
			user.setAvatar("/asset/private/user/" + userDto.getUserName() + "_" + fileName);
			try {
				FileCopyUtils.copy(upAvatar.getBytes(),
						new File("D:\\thang\\git\\youtube-fake\\src\\main\\resources\\static\\asset\\private\\user\\"
								+ userDto.getUserName() + "_" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		UserEntity entity = userRepository.save(user);
		UserDTO dto = converter.convertToDto(entity);
		return dto;
	}

	@Override
	public UserEntity findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<UserEntity> entity = userRepository.findById(id);
		return converter.convertToDto(entity.get());
	}

	@Override
	public List<UserDTO> findAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return users.stream().map((user) -> converter.convertToDto(user)).collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> searchUser(String search_key) {
		List<UserEntity> entities = userRepository.findByFullNameContaining(search_key);
		return entities.stream().map((entity) -> converter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteUsers(long[] ids) {
		for (long id : ids) {
			userRepository.deleteById(id);
		}
	}

}
