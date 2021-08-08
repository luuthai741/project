package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.dto.ItemsDTO;
import com.project.entities.Cart;
import com.project.entities.CartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
	@Query("SELECT new com.project.dto.ItemsDTO(i.id,i.quantity, b.id, b.name, b.author,b.image, b.price,b.quantity,c.id)"
			+ "FROM CartItem i INNER JOIN i.cart c INNER JOIN i.book b where c.id = ?1 ")
	List<ItemsDTO> joinCartBookByCartId(int id);
	
	@Query("DELETE FROM CartItem i WHERE i.cart=?1")
	void deleteAllByCart(Cart cart);
}
