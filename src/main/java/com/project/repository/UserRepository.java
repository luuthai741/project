package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.dto.TokenDTO;
import com.project.dto.UserInfoDTO;
import com.project.entities.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
	@Query("UPDATE User u SET u.enabled = true where u.id=?1")
	@Modifying
	public void enable(Integer id);
	
	@Query("SELECT new com.project.dto.UserInfoDTO(u.username,u.email,i.firstName, i.lastName, i.phoneNumber, i.address)"
			+ "FROM User u INNER JOIN u.info i WHERE u.id =?1")
	UserInfoDTO joinInfo(int userId);
	
	@Query("SELECT new com.project.dto.TokenDTO(t.id,t.verifyToken,t.resetToken,u.id)"
			+ "FROM User u INNER JOIN u.token t WHERE u.id =?1")
	TokenDTO joinToken(int userId);
}
