package org.balu.learn.userservice.transformer;

import java.util.List;
import java.util.stream.Collectors;

import org.balu.learn.userservice.dto.PermissionDTO;
import org.balu.learn.userservice.entity.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionTransformer {
	@Autowired
	private ModelMapper mapper;
	
	public PermissionDTO convertToDTO(Permission permission) {
		return mapper.map(permission, PermissionDTO.class);
	}
	
	public Permission convertToEntity(PermissionDTO permissionDTO) {
		return mapper.map(permissionDTO, Permission.class);
	}
	
	public List<PermissionDTO> convertToDTO(List<Permission> permissions){
		return permissions
				.stream()
				.map(permission -> mapper.map(permission, PermissionDTO.class))
				.collect(Collectors.toList());
	}
}