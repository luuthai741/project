package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.security.Role;
import com.project.entities.security.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(RoleType type);
}
