package org.balu.learn.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInDTO {
	private Long id;
	private String username;
	private String password;	
}
