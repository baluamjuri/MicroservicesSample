package org.balu.learn.security.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.security.auth.entity.Permission;
import org.balu.learn.security.auth.entity.Role;
import org.balu.learn.security.auth.entity.User;
import org.balu.learn.security.auth.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		
		/*List<SimpleGrantedAuthority> authorities = user.getRoles()
													.stream()
													.map(role -> new SimpleGrantedAuthority(SPRING_ROLE_PREFIX+role.getName()))
													.collect(Collectors.toList());*/
		
		List<SimpleGrantedAuthority> authorities = user.getRoles()
														.stream()
														.parallel()
														.map(Role::getPermisssions)
														.flatMap(List::stream)
														.distinct()
														.map(this::toGrantedAuthority)
														.collect(Collectors.toList());
		
		logger.info("Spring User get loading with the User details Username,password and authorities");
		
		return new org.springframework.security.core.userdetails.User(
														user.getUsername(), 
														user.getPassword(), 
														authorities);
		
	}

	private SimpleGrantedAuthority toGrantedAuthority(Permission permission) {
		return new SimpleGrantedAuthority(SPRING_ROLE_PREFIX+permission.getName());
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
}
