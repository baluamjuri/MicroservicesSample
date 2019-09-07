package org.balu.learn.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long id;
	private String username;
	private String displayName;
	private String email;
	private String location;
	private String language;
}
