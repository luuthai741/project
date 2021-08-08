package com.project.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.dto.CartDTO;
import com.project.dto.UserInfoDTO;
import com.project.entities.Cart;
import com.project.repository.CartRepository;
import com.project.repository.UserRepository;

import lombok.AllArgsConstructor;

@Controller("adminOrderController")
@RequestMapping("/admin/orders")
@AllArgsConstructor
public class OrderController {
	private CartRepository cartRepo;
	private UserRepository userRepo;
	@GetMapping
	public String  orders(Model model) {
		List<CartDTO> orders = cartRepo.joinUser();
		List<UserInfoDTO> listUserInfo = new ArrayList<>();
		model.addAttribute("orders", orders);
		return "admin/orders/index";
	}
	@GetMapping("/{userId}")
	private String detail(Model model, @PathVariable int userId) {
		UserInfoDTO userInfo = userRepo.joinInfo(userId);
		model.addAttribute("info", userInfo);
		return "admin/orders/detail";
	}
	
	@GetMapping("/delete/{id}")
	private String delete( @PathVariable int id) {
		cartRepo.deleteById(id);
		return "redirect:/admin/orders";
	}
}
