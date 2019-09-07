package org.balu.learn.userservice.transformer;

import java.util.List;
import java.util.stream.Collectors;

import org.balu.learn.userservice.dto.RoleDTO;
import org.balu.learn.userservice.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleTransformer {
	@Autowired
	private ModelMapper mapper;
	
	public RoleDTO convertToDTO(Role role) {
		return mapper.map(role, RoleDTO.class);
	}
	
	public Role convertToEntity(RoleDTO roleDTO) {
		return mapper.map(roleDTO, Role.class);
	}
	
	public List<RoleDTO> convertToDTO(List<Role> roles) {
		return roles.stream()
			.map(role -> mapper.map(role, RoleDTO.class))
			.collect(Collectors.toList());
	}
}