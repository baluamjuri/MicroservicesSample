package org.balu.learn.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_permission")
@Where(clause="is_active=1")
@SQLDelete(sql="UPDATE t_permission SET is_active=0 WHERE permission_id=?")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="permission_id")
	private Long id;
	
	@Column(name="permission_name", nullable=false, unique=true)
	private String name;
	
	@Column(name="is_active", nullable=false)
	private boolean active;
	
	private String description;
}
