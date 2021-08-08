package com.project.controller.user;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.project.ultil.Ultil;

import lombok.AllArgsConstructor;

@Controller("cartController")
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController extends BaseController {
	private CartItemRepository itemRepo;
	private CartRepository cartRepo;
	private BookService bookService;
	private CartService cartService;
	private UserService userService;
	private CartItemRepository cartItemRepo;
	private SendMail sendMail;
	
	@GetMapping
	public String cart(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
		User user = userService.findById(userPrincipal.getId());
		if (user.getCart() == null) {
			Cart cart = new Cart();
			cart.setUser(user);
			cartRepo.save(cart);
			return "user/cart/index";
		} else {
			CartDTO cartDTO = cartRepo.joinUserByUserId(user.getId());
			List<ItemsDTO> itemsDTO = itemRepo.joinCartBookByCartId(cartDTO.getId());
			model.addAttribute("items", itemsDTO);
			model.addAttribute("total", cartDTO.getTotal());
			return "user/cart/index";
		}

	}

	@GetMapping("/add")
	public String add(@RequestParam int bookId, @RequestParam int quantity) {
		cartService.addToCart(bookId, quantity);
		return "redirect:/books";
	}

	@PostMapping("update")
	public String updateCart(@RequestParam int itemId, @RequestParam int quantity) {
		CartItem item = itemRepo.findById(itemId).orElse(null);
		item.setQuantity(item.getQuantity() + quantity);
		itemRepo.save(item);
		return "redirect:/cart";
	}

	@GetMapping("/delete")
	public String add(@RequestParam int id) {
		cartService.deleteItem(id);
		return "redirect:/cart";
	}

	@PostMapping("/check-out")
	public String checkOut(HttpServletRequest request) {
		UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userPrinciple.getUser();
		if(user.getInfo() == null){
			return "redirect:/info";
		}
		String link = Ultil.getSiteURL(request);
		cartService.checkOut(user, link);
		return "redicrect:/";
	}
	
	@GetMapping("/check-out")
	public String checkOut(@Param ("token") String token) {
		if(cartService.confirm(token)) {
			return "redirect:/";
		}
		return "redirect:/";
	}

}
