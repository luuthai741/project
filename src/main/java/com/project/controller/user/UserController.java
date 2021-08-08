package com.project.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Book;
import com.project.entities.UserInfo;
import com.project.entities.security.User;
import com.project.repository.InfoRepository;
import com.project.security.UserPrinciple;
import com.project.service.UserService;

import lombok.AllArgsConstructor;

@Controller("userController")
@PreAuthorize("hasRole('USER')")
@AllArgsConstructor
@RequestMapping("/info")
public class UserController extends BaseController{
	private UserService userService;
	private InfoRepository infoRepo;
	@GetMapping
	public String getInfo(Model model) {
		UserPrinciple userPrinciple= (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userPrinciple.getUser();
		if(user.getInfo() != null) {
			model.addAttribute("user", user);
			model.addAttribute("info", user.getInfo());
			return "user/info/index";
		}
		return "redirect:/info/add";
	}
	
	@GetMapping("/add")
	public String addInfo(Model model) {
		UserInfo info = new UserInfo();
		model.addAttribute("info",info);
		return "user/info/form";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute(name = "info") UserInfo info) {
		UserPrinciple userPrinciple= (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userPrinciple.getUser();
		info.setUser(user);
		infoRepo.save(info);	
		return "redirect:/";
	}
	@GetMapping("/update")
	public String update(Model model,@RequestParam int id) {
		UserInfo info = infoRepo.findById(id).orElse(null);
		if(info !=null) {
			model.addAttribute("info",info);
			return "user/info/form";
		}
		return "redirect:/info/add";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute(name = "info") UserInfo info) {
		UserPrinciple userPrinciple= (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userPrinciple.getUser();
		info.setUser(user);
		infoRepo.save(info);	
		return "redirect:/";
	}
}
