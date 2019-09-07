package org.balu.learn.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.balu.learn.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	List<Role> findAll();
	Optional<Role> findById(Long id);
	Role getById(Long id);
}

