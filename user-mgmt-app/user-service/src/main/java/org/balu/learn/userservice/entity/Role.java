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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_role")
@Where(clause="is_active=1")
@SQLDelete(sql="UPDATE t_role SET is_active=0 WHERE role_id=?")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
	
	@Column(name="role_name", nullable=false, unique=true)
	private String name;
	
	@Column(name = "is_active", nullable=false)
	private Boolean active;
	private String description;
	
//	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	@ManyToMany
	@JoinTable(
            name="t_role_permission",
            joinColumns = @JoinColumn( name="role_id"),
            inverseJoinColumns = @JoinColumn( name="permission_id"))
	private List<Permission> permissions;
}
