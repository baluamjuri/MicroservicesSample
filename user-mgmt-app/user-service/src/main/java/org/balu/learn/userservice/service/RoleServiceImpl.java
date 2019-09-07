package org.balu.learn.userservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.userservice.dto.PermissionDTO;
import org.balu.learn.userservice.dto.RoleDTO;
import org.balu.learn.userservice.entity.Permission;
import org.balu.learn.userservice.entity.Role;
import org.balu.learn.userservice.repository.PermissionRepository;
import org.balu.learn.userservice.repository.RoleRepository;
import org.balu.learn.userservice.transformer.PermissionTransformer;
import org.balu.learn.userservice.transformer.RoleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.util.StringUtils;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private static final Logger logger = LogManager.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private RoleTransformer roleTransformer;
	
	@Autowired
	private PermissionTransformer permissionTransformer;
	
	@Override
	public RoleDTO findById(Long id) {
		Optional<Role> proxy = roleRepository.findById(id);
		if(proxy.isPresent()) {
			Role role = proxy.get();
			return roleTransformer.convertToDTO(role); 
		}
		return null;
	}

	@Override
	public Long save(RoleDTO roleDTO) {
		Role roleEntity = roleTransformer.convertToEntity(roleDTO); 
		roleEntity.setId(null);
		roleEntity.setActive(true); 
		Role createdRole = roleRepository.save(roleEntity);
		logger.info("New Role created...");
		return createdRole.getId();
	}

	@Override
	public RoleDTO update(Long id, RoleDTO roleDTO) {
		Optional<Role> proxy = roleRepository.findById(id);
		if(!proxy.isPresent()) 
			return null;
		Role persistentRole = proxy.get();
		if(StringUtils.isNotBlank(roleDTO.getName()))
			persistentRole.setName(roleDTO.getName());
		
		if(StringUtils.isNotBlank(roleDTO.getDescription()))
			persistentRole.setDescription(roleDTO.getDescription());
		
		Role updatedRole =  roleRepository.save(persistentRole);
		logger.info("Role Information updated sucessfully.");
		return roleTransformer.convertToDTO(updatedRole); 
	}

	@Override
	public void delete(Long id) {
		Role lazyUser = roleRepository.getOne(id);
		roleRepository.delete(lazyUser);
	}

	@Override
	public List<RoleDTO> findAll() {
		List<Role> roles = roleRepository.findAll();
		List<RoleDTO> roleDTOs = roleTransformer.convertToDTO(roles); 
		return roleDTOs;
	}
	
	@Override
	public List<PermissionDTO> findPermissionsByRoleId(Long id) {
		Optional<Role> proxy = roleRepository.findById(id);
		if(!proxy.isPresent()) 
			return null;
		Role role = proxy.get();
		List<PermissionDTO> permissionDTOs = permissionTransformer.convertToDTO(role.getPermissions());
		return permissionDTOs;
	}

	@Override
	public boolean addPermissionToRole(Long roleId, Long permissionId) {
		return updatePermissionToRole(roleId, permissionId, true);
	}

	@Override
	public boolean deletePermissionFromRole(Long roleId, Long permissionId) {
		return updatePermissionToRole(roleId, permissionId, false);
	}
	
	//Add or delete a role 
	private boolean updatePermissionToRole(Long roleId, Long permissionId, boolean isAdd) {
		Optional<Role> proxyRole = roleRepository.findById(roleId);
		
		if(!proxyRole.isPresent())
			return false;
		
		Permission lazyPermission = permissionRepository.getById(permissionId);	
		
		if(lazyPermission==null) {
			return false;
		}
		
		Role persistentRole = proxyRole.get();
		List<Permission> existingPermissions = persistentRole.getPermissions();
		if(isAdd) {		
			//The permission is already mapped!
			if(existingPermissions.contains(lazyPermission))
				return false;
			else
				persistentRole.getPermissions().add(lazyPermission);
		}
		else {
			if(existingPermissions.contains(lazyPermission))
				persistentRole.getPermissions().remove(lazyPermission);
			else
				return false;
		}
		
		roleRepository.save(persistentRole);
		return true;
	}
}