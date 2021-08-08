package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.dto.CartDTO;
import com.project.entities.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
	@Query("SELECT new com.project.dto.CartDTO(c.id,c.total,c.enabled, u.id, u.username, u.email)"
			+ "FROM Cart c INNER JOIN c.user u WHERE u.id=?1")
	CartDTO joinUserByUserId(int id);
	
	@Query("SELECT new com.project.dto.CartDTO(c.id,c.total,c.enabled, u.id, u.username, u.email)"
			+ "FROM Cart c INNER JOIN c.user u")
	List<CartDTO> joinUser();
	
	@Query("SELECT c FROM Cart c where c.token =?1")
	Cart findByToken(String token);
}
