package org.balu.learn.userservice.service;

import java.util.List;

import org.balu.learn.userservice.dto.PermissionDTO;
import org.balu.learn.userservice.dto.RoleDTO;

public interface RoleService {
	List<RoleDTO> findAll();
	//RoleDTO findByName(String roleName);
	RoleDTO findById(Long id) ;
	Long save(RoleDTO roleDTO);
	RoleDTO update(Long id, RoleDTO roleDTO);
	void delete(Long id);
	
	List<PermissionDTO> findPermissionsByRoleId(Long id) ;
	boolean addPermissionToRole(Long roleId, Long permissionId);
	boolean deletePermissionFromRole(Long roleId, Long permissionId);
}