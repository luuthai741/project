package com.project.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.dto.CartDTO;
import com.project.dto.ItemsDTO;
import com.project.entities.Book;
import com.project.entities.Cart;
import com.project.entities.CartItem;
import com.project.entities.security.User;
import com.project.mail.SendMail;
import com.project.repository.CartItemRepository;
import com.project.repository.CartRepository;
import com.project.security.UserPrinciple;
import com.project.service.BookService;
import com.project.service.CartService;
import com.project.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
	private CartRepository cartRepo;
	private CartItemRepository itemRepo;
	private BookService bookService;
	private SendMail sendMail;
	@Override
	public void addToCart(int bookId, int quantity) {
		UserPrinciple userPrincipal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		CartDTO cartDTO = cartRepo.joinUserByUserId(userPrincipal.getId());
		Cart cart = cartRepo.findById(cartDTO.getId()).orElse(null);
		Book book = bookService.findById(bookId);
		cardHandler(cart, book, quantity);
	}
	
	private void cardHandler(Cart cart, Book book, int quantity) {
		CartItem cartItem = null;
		if (cart.getItems().size() == 0) {
			cartItem = new CartItem();
			cartItem.setBook(book);
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);
			itemRepo.save(cartItem);
		}

		for (CartItem item : cart.getItems()) {
			if (item.getBook().getId() == book.getId()) {
				item.setQuantity(item.getQuantity() + quantity);	
				cartItem = item;
				itemRepo.save(item);	
				break;
			}
		}	
		if(cartItem == null) {
			cartItem = new CartItem();
			cartItem.setBook(book);
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);
			itemRepo.save(cartItem);
		}
	}
	
	@Override
	public void deleteItem(int id) {
		UserPrinciple userPrincipal = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		CartDTO cartDTO = cartRepo.joinUserByUserId(userPrincipal.getId());
		Cart cart = cartRepo.findById(cartDTO.getId()).orElse(null);
		CartItem item = itemRepo.findById(id).orElse(null);
		if(item != null && cart != null) {
			itemRepo.deleteById(id);
		}		
	}

	@Override
	public void checkOut(User user, String link) {
		CartDTO cartDTO = cartRepo.joinUserByUserId(user.getId());
		Cart cart = cartRepo.findById(cartDTO.getId()).orElse(null);
		int total = 0;
		for(CartItem item : cart.getItems()) {
			total += item.getQuantity() * item.getBook().getPrice();
		}
		cart.setTotal(total);
		cart.setToken(UUID.randomUUID().toString());
		cartRepo.save(cart);	
		try {
			sendMail.checkMail(user, link, cart.getToken());
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean confirm(String token) {
		Cart cart = cartRepo.findByToken(token);
		if(cart.getItems().size() <= 0 || cart.isEnabled() == true) {
			return false;
		}
		if(cart != null) {
			cart.setEnabled(true);
			cartRepo.save(cart);
			List<ItemsDTO> itemsDTO = itemRepo.joinCartBookByCartId(cart.getId());
			for(ItemsDTO item : itemsDTO) {
				Book book = bookService.findById(item.getBookId());
				book.setQuantity(book.getQuantity() - item.getItemQuantity());
				bookService.save(book);
			}
			itemRepo.deleteAllByCart(cart);
		}	
		return true;
	}

}
