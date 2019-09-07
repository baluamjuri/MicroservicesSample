package org.balu.learn.userservice.transformer;

import org.balu.learn.userservice.dto.LogInDTO;
import org.balu.learn.userservice.dto.UserDTO;
import org.balu.learn.userservice.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {
	@Autowired
	private ModelMapper mapper;
	
	public UserDTO convertToDTO(User user) {
		return mapper.map(user, UserDTO.class);
	}
	
	public User convertToEntity(UserDTO userDTO) {
		return mapper.map(userDTO, User.class);
	}
	
	public User convertToEntity(LogInDTO logInDTO) {
		return mapper.map(logInDTO, User.class);
	}
}
