package org.balu.learn.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.userservice.dto.LogInDTO;
import org.balu.learn.userservice.dto.RoleDTO;
import org.balu.learn.userservice.dto.UserDTO;
import org.balu.learn.userservice.entity.Role;
import org.balu.learn.userservice.entity.User;
import org.balu.learn.userservice.repository.RoleRepository;
import org.balu.learn.userservice.repository.UserRepository;
import org.balu.learn.userservice.transformer.RoleTransformer;
import org.balu.learn.userservice.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserTransformer userTransformer;
	
	@Autowired
	private RoleTransformer roleTransformer;
	
	@Override
	public UserDTO findByUsername(String username) {
		Optional<User> proxy = userRepository.findByUsername(username);
		if(proxy.isPresent()) {
			User user = proxy.get();
			return userTransformer.convertToDTO(user);
		}
		return null;
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<User> proxy = userRepository.findById(id);
		if(proxy.isPresent()) {
			User user = proxy.get();
			return userTransformer.convertToDTO(user);
		}
		return null;
	}

	@Override
	public Long save(LogInDTO logInDTO) {
		User userEntity = userTransformer.convertToEntity(logInDTO); 
		userEntity.setPassword(
				new BCryptPasswordEncoder().encode(logInDTO.getPassword()));
		userEntity.setId(null);
		userEntity.setActive(true);
		User createdUser = userRepository.save(userEntity);
		logger.info("New User created...");
		return createdUser.getId();
	}

	@Override
	public UserDTO update(Long id, UserDTO userDTO) {
		Optional<User> proxy = userRepository.findById(id);
		if(!proxy.isPresent()) 
			return null;
		User persistentUser = proxy.get();
		
		if(StringUtils.isNotBlank(userDTO.getUsername()))
			persistentUser.setUsername(userDTO.getUsername());
		
		if(StringUtils.isNotBlank(userDTO.getDisplayName()))
			persistentUser.setDisplayName(userDTO.getDisplayName());
		
		if(StringUtils.isNotBlank(userDTO.getEmail()))
			persistentUser.setEmail(userDTO.getEmail());
		
		User updatedUser =  userRepository.save(persistentUser);
		logger.info("User Information updated sucessfully.");
		return userTransformer.convertToDTO(updatedUser); 
	}

	@Override
	public void delete(Long id) {		
		User lazyUser = userRepository.getOne(id);
		userRepository.delete(lazyUser);		
	}

	@Override
	public List<UserDTO> findAll() {
		List<User> users = userRepository.findAll();
		List<UserDTO> userDTOs = users.stream()
									.map(userTransformer::convertToDTO)
									.collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public boolean changePassword(Long id, LogInDTO logInDTO) {
		Optional<User> proxy = userRepository.findById(id);
		if(!proxy.isPresent()) 
			return false;
		User persistentUser = proxy.get();
		persistentUser.setPassword(
				new BCryptPasswordEncoder().encode(logInDTO.getPassword()));
		userRepository.save(persistentUser);
		logger.info("Password successfully changed...");
		return true;
	}

	@Override
	public List<RoleDTO> findRolesByUserId(Long id) {
		Optional<User> proxy = userRepository.findById(id);
		if(!proxy.isPresent()) 
			return null;
		User user = proxy.get();
		List<RoleDTO> roleDTOs = user.getRoles()
								.stream()
								.map(roleTransformer::convertToDTO)
								.collect(Collectors.toList());
		return roleDTOs;
	}

	@Override
	public boolean addRoleToUser(Long userId,Long roleId) {
		return updateRolesToUser(userId, roleId, true);
	}

	@Override
	public boolean deleteRoleFromUser(Long userId,Long roleId) {
		return updateRolesToUser(userId, roleId, false);
	}
	
	//Adds or delete a role 
	private boolean updateRolesToUser(Long userId,Long roleId, boolean isAdd) {
		Optional<User> proxyUser = userRepository.findById(userId);
		
		if(!proxyUser.isPresent())
			return false;
		
		Role lazyRole = roleRepository.getOne(roleId);
		
		if(lazyRole==null)
			return false;
		
		User persistentUser = proxyUser.get();
		
		List<Role> existingRoles = persistentUser.getRoles();
		
		if(isAdd) {		
			//The role is already mapped!
			if(existingRoles.contains(lazyRole))
				return false;
			else
				persistentUser.getRoles().add(lazyRole);
		}
		else {
			if(existingRoles.contains(lazyRole))
				persistentUser.getRoles().remove(lazyRole);
			else
				return false;
		}
		
		userRepository.save(persistentUser);
		return true;
	}
}