package org.balu.learn.userservice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_user")
@Where(clause="is_active=1")
@SQLDelete(sql="UPDATE t_user SET is_active=0 WHERE id=?")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z]{3,}")
	@Column(nullable=false, unique=true)
	private String username;	
	
	@NotNull	
	@Column(nullable=false)
	private String password;
	
	private String displayName;
	
	@Email
	private String email;
	
	private String location;
	private String language;
	
	@Column(name="is_active", nullable=false)
	private Boolean active;
	
//	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH})
	@ManyToMany
	@JoinTable(
            name="t_user_role",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="role_id"))
	private List<Role> roles;
}
