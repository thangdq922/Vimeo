package com.example.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.dto.MyUser;
import com.example.entity.UserEntity;

public class SecurityUtil {
	public static UserEntity getPrincipal() {
		return ((MyUser) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUser();
        
    }
}
