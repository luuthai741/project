package com.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.repository.CartItemRepository;
import com.project.repository.CartRepository;

@RestController
@RequestMapping("/api/test")
@PreAuthorize("hasRole('ADMIN')")
public class TestController {
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private CartItemRepository itemRepo;
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam int id){
		return ResponseEntity.ok(itemRepo.joinCartBookByCartId(id));

}
}	