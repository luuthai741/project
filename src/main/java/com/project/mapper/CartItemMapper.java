package com.project.mapper;

import org.springframework.stereotype.Component;

import com.project.repository.CartItemRepository;
import com.project.repository.CartRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CartItemMapper {
	private CartItemRepository itemRepo;
	private CartRepository cartRepo;
	
	
}
