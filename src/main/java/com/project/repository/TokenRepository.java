package com.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.dto.TokenDTO;
import com.project.entities.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
	@Query("SELECT t from Token t where t.verifyToken = ?1")
	Token findByVerifyToken(String token);
	Token findByResetToken(String token);
	
	@Query("SELECT new com.project.dto.TokenDTO(t.id,t.verifyToken,t.resetToken,u.id)"
			+ "FROM Token t INNER JOIN t.user u WHERE t.id =?1")
	TokenDTO joinUser(int id);
}
