package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.MyUser;
import com.example.entity.UserEntity;
import com.example.repository.UserRepository;

@Service
public class CusTomDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserName(username);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException("User no found");
		}
		return new MyUser(userEntity);
		

	}

}
