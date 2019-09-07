package org.balu.learn.userservice.service;

import java.util.List;

import org.balu.learn.userservice.dto.LogInDTO;
import org.balu.learn.userservice.dto.RoleDTO;
import org.balu.learn.userservice.dto.UserDTO;

public interface UserService {
	List<UserDTO> findAll();
	UserDTO findByUsername(String username);
	UserDTO findById(Long id) ;
	Long save(LogInDTO logInDTO);
	UserDTO update(Long id, UserDTO userDTO);
	void delete(Long id);
	boolean changePassword(Long id, LogInDTO logInDTO);
	List<RoleDTO> findRolesByUserId(Long id);
	boolean addRoleToUser(Long userId, Long roleId);
	boolean deleteRoleFromUser(Long userId, Long roleId);
}