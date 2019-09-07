package org.balu.learn.security.auth.repository;

import org.balu.learn.security.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	User findByUsername(String username);
}
