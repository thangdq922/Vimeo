package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserDTO
{
	private Long id;
	
	@NotBlank(message = "username have blank")
	private String userName;
	@NotEmpty(message = "name is empty")
	private String fullName;
	@NotBlank(message = "password have blank")
	private String password;
	@NotBlank(message = "email have blank")
	@Email(message = "Email is not valid", regexp = ".+[@].+[\\.].+" )
	private String email;

	private String avatar;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
	
}
