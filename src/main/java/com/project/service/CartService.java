package com.project.service;

import com.project.entities.security.User;

public interface CartService {
	public void addToCart(int bookId, int quantity);
	public void deleteItem(int id);
	public void checkOut(User user, String link);
	public boolean confirm(String token);
}
