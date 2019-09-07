package org.balu.learn.userservice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.userservice.dto.PermissionDTO;
import org.balu.learn.userservice.dto.RoleDTO;
import org.balu.learn.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {
	
	private static final Logger logger = LogManager.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("{id}")
	@PreAuthorize("hasRole('ROLES_READ')")
	public ResponseEntity<RoleDTO> findById(@PathVariable("id") Long id) {
		logger.info("Role Information requested by ID: "+id);
		RoleDTO roleDTO = roleService.findById(id);
		if(roleDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(roleDTO);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLES_READ')")
	public ResponseEntity<List<RoleDTO>> findAll(){
		logger.info("All Roles Information reqested");
		List<RoleDTO> roleDTOs = roleService.findAll();
		
		//If there are no active roles in the database
		if(roleDTOs.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(roleDTOs);		
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLES_CREATE')")
	public ResponseEntity<Object> save(@RequestBody RoleDTO roleDTO){
		Long id = roleService.save(roleDTO);
		logger.info("New Role Added with id: "+id);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ROLES_UPDATE')")
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody RoleDTO roleDTO){
		logger.info("Update Role Information requested for id: "+id);
		RoleDTO updatedRoleDTO = roleService.update(id, roleDTO);
		if(updatedRoleDTO == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	
    @DeleteMapping(value="/{id}")
	@PreAuthorize("hasRole('ROLES_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id){
    	logger.info("Delete Role Information requested for id: "+id);
		roleService.delete(id);
		return ResponseEntity.ok().build();
    }
    
    @GetMapping("{roleId}/permissions")
	@PreAuthorize("hasRole('ROLES_PERMISSION_MAPPING')")
	public ResponseEntity<List<PermissionDTO>> findPermissionsByRoleId(@PathVariable Long roleId){
		logger.info("All Permission for Roles Information reqested");
		List<PermissionDTO> permissionDTOs = roleService.findPermissionsByRoleId(roleId);
		if(permissionDTOs==null)
			return ResponseEntity.notFound().build();
		else if(permissionDTOs.isEmpty())
			return ResponseEntity.noContent().build();
		else 
			return ResponseEntity.ok(permissionDTOs);	
	}
	
	@PutMapping("{roleId}/permissions/{permissionId}")
	@PreAuthorize("hasRole('ROLES_PERMISSION_MAPPING')")
	public ResponseEntity<RoleDTO> addPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId){
		logger.info("Add permission to role ");		
		boolean added = roleService.addPermissionToRole(roleId, permissionId);
		if(added){
			return ResponseEntity.ok().build();
		}
		//Either roleId or permissionId doesn't exist
		return ResponseEntity.badRequest().build();
	}
		
	@DeleteMapping("{roleId}/permissions/{permissionId}")
	@PreAuthorize("hasRole('ROLES_PERMISSION_MAPPING')")
    public ResponseEntity<RoleDTO> deletePermissionFromRole(@PathVariable Long roleId, @PathVariable Long permissionId){
    	logger.info("Delete permission from role ");
		boolean deleted = roleService.deletePermissionFromRole(roleId, permissionId);
		if(deleted){
			return ResponseEntity.ok().build();
		}
		//Either roleId or permissionId doesn't exist
		return ResponseEntity.badRequest().build();
 
    }
}
