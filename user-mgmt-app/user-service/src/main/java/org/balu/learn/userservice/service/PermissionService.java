package org.balu.learn.userservice.service;

import java.util.List;

import org.balu.learn.userservice.dto.PermissionDTO;

public interface PermissionService {
	List<PermissionDTO> findAll();
	PermissionDTO findById(Long id) ;
	Long save(PermissionDTO permissionDTO);
	PermissionDTO update(Long id, PermissionDTO permissionDTO);
	void delete(Long id);
}
