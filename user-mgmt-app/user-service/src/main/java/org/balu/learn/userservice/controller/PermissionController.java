package org.balu.learn.userservice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.userservice.dto.PermissionDTO;
import org.balu.learn.userservice.service.PermissionService;
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
@RequestMapping("permissions")
public class PermissionController {
	
	private static final Logger logger = LogManager.getLogger(PermissionController.class);
	@Autowired
	private PermissionService permissionService;
	
	@GetMapping
	@PreAuthorize("hasRole('PERMISSION_READ')")
	public ResponseEntity<List<PermissionDTO>> findAll(){
		List<PermissionDTO> permissionDTOs = permissionService.findAll();
		
		//If there are no active roles in the database
		if(permissionDTOs.isEmpty()) 
			ResponseEntity.noContent().build();
		return ResponseEntity.ok(permissionDTOs);		
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasRole('PERMISSION_READ')")	
	public ResponseEntity<PermissionDTO> findById(@PathVariable Long id) {
		logger.info("Permission Information requested by ID: "+id);
		PermissionDTO permissionDTO = permissionService.findById(id);
		
		//If the permission is not found
		if(permissionDTO == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(permissionDTO);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('PERMISSION_CREATE')")
	public ResponseEntity<Object> save(@RequestBody PermissionDTO permissionDTO){
		Long id = permissionService.save(permissionDTO);
		logger.info("New Permission Added with id: "+id);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('PERMISSION_UPDATE')")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO){
		logger.info("Update Permission Information requested for id: "+id);
		PermissionDTO updatedPermissionDTO = permissionService.update(id, permissionDTO);
		if(updatedPermissionDTO == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	
    @DeleteMapping(value="/{id}")
	@PreAuthorize("hasRole('PERMISSION_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id){
    	permissionService.delete(id);
		return ResponseEntity.ok().build();
    }
}