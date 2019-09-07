package org.balu.learn.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.balu.learn.userservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	List<Permission> findAll();
	Optional<Permission> findById(Long id);
	Permission getById(Long id);
}
