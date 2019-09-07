package org.balu.learn.security.auth.service;

import org.balu.learn.security.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	public static final String SPRING_ROLE_PREFIX = "ROLE_";
	
	User findByUsername(String username);
}
