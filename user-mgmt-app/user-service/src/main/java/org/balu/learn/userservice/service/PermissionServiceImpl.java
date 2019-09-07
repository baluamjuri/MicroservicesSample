package org.balu.learn.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.userservice.dto.PermissionDTO;
import org.balu.learn.userservice.entity.Permission;
import org.balu.learn.userservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	private static final Logger logger = LogManager.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private org.balu.learn.userservice.transformer.PermissionTransformer transformer;
	
	@Override
	public List<PermissionDTO> findAll() {
		List<Permission> permissions = permissionRepository.findAll();
		List<PermissionDTO> permissionDTOs = permissions.stream()
									.map(permission -> transformer.convertToDTO(permission))
									.collect(Collectors.toList());
		return permissionDTOs;
	}

	@Override
	public PermissionDTO findById(Long id) {
		Optional<Permission> proxy = permissionRepository.findById(id);
		if(proxy.isPresent()) {
			Permission permission = proxy.get();
			return transformer.convertToDTO(permission);
		}
		return null;
	}

	@Override
	public Long save(PermissionDTO permissionDTO) {
		Permission permissionEntity = transformer.convertToEntity(permissionDTO); 
		permissionEntity.setId(null);
		permissionEntity.setActive(true);
		Permission createdPermission = permissionRepository.save(permissionEntity);
		logger.info("New Permission created...");
		return createdPermission.getId();
	}

	@Override
	public PermissionDTO update(Long id, PermissionDTO permissionDTO) {
		Optional<Permission> proxy = permissionRepository.findById(id);
		if(!proxy.isPresent()) 
			return null;
		Permission persistentPermission = proxy.get();
		
		if(StringUtils.isNotBlank(permissionDTO.getName()))
			persistentPermission.setName(permissionDTO.getName());
		
		if(StringUtils.isNotBlank(permissionDTO.getDescription()))
			persistentPermission.setDescription(permissionDTO.getDescription());
			
		Permission updatedPermission =  permissionRepository.save(persistentPermission);		
		logger.info("Permission Information updated sucessfully.");
		return transformer.convertToDTO(updatedPermission);
	}
	
	@Override
	public void delete(Long id) {
		Permission lazyPermission = permissionRepository.getOne(id);
		permissionRepository.delete(lazyPermission);		
	}

}
