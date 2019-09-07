package org.balu.learn.userservice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.balu.learn.userservice.dto.LogInDTO;
import org.balu.learn.userservice.dto.RoleDTO;
import org.balu.learn.userservice.dto.UserDTO;
import org.balu.learn.userservice.service.UserService;
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
@RequestMapping("users")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@GetMapping("{id}")
	@PreAuthorize("hasRole('USER_READ')")
	public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
		logger.info("User Information requested by ID: "+id);
		UserDTO userDTO = userService.findById(id);
		
		//If the user is not found
		if(userDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDTO);
	}
	
	@GetMapping("name/{name}")
	@PreAuthorize("hasRole('USER_READ')")
	public ResponseEntity<UserDTO> findByUsername(@PathVariable("name") String name ){
		logger.info("User Information requested by name: "+name);
		UserDTO userDTO = userService.findByUsername(name);
		
		//If the username doesn't exist
		if(userDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDTO);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('USER_READ')")
	public ResponseEntity<List<UserDTO>> findAll(){
		logger.info("All Users Information reqested");
		List<UserDTO> userDTOs = userService.findAll();

		//If there are no active users in the database
		if(userDTOs.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(userDTOs);		
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER_CREATE')")
	public ResponseEntity<Void> save(@RequestBody LogInDTO logInDTO){
		Long id;
		id = userService.save(logInDTO);
		logger.info("New User Added with id: "+id);
		return ResponseEntity.status(HttpStatus.CREATED).build();//201
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasRole('USER_UPDATE')")
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO){
		logger.info("Update User Information requested for id: "+id);
		UserDTO updatedUserDTO = userService.update(id, userDTO);
		if(updatedUserDTO == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("{id}/changepassword")
	@PreAuthorize("hasRole('PASSWORD_UPDATE')")
	public ResponseEntity<Object> changePassword(@PathVariable("id") Long id, @RequestBody LogInDTO logInDTO) {
		logger.info("Change User password requested for id: " + id);
		boolean isUpdated = userService.changePassword(id, logInDTO);
		if (isUpdated)
			return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('USER_DELETE')")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("{id}/roles")
	@PreAuthorize("hasRole('USER_ROLES_MAPPING')")
	public ResponseEntity<List<RoleDTO>> getRolesByUserId(@PathVariable("id") Long id) {
		List<RoleDTO> roleDTOs = userService.findRolesByUserId(id);
		if (roleDTOs == null)
			return ResponseEntity.notFound().build();
		else if (roleDTOs.isEmpty())
			return ResponseEntity.noContent().build();// 204
		else
			return ResponseEntity.ok(roleDTOs);
	}
	
	@PutMapping("{userId}/roles/{roleId}")
	@PreAuthorize("hasRole('USER_ROLES_MAPPING')")
	public ResponseEntity<Object> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
		boolean isAdded = userService.addRoleToUser(userId, roleId);
		if (isAdded) {
			return ResponseEntity.ok().build();
		}
		// Either userId or roleId doesn't exist
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("{userId}/roles/{roleId}")
	@PreAuthorize("hasRole('USER_ROLES_MAPPING')")
	public ResponseEntity<Object> deleteRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId){
		boolean deleted = userService.deleteRoleFromUser(userId, roleId);
		if(deleted){
			return ResponseEntity.ok().build();
		}
		//Either userId or roleId doesn't exist
		return ResponseEntity.badRequest().build();
	}
}