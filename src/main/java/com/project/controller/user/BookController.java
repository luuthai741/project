package com.project.controller.user;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.service.BookService;
import com.project.service.GenreService;

import lombok.AllArgsConstructor;

@Controller("userBookController")
@RequestMapping("/books")
@AllArgsConstructor
public class BookController extends BaseController{
	private BookService bookService;
	private GenreService genreService;
	@GetMapping
	public String home(Model model) {
		model.addAttribute("books", bookService.findAllByEnabled());
		return "user/books/index";
	}
	
	@GetMapping("/{id}")
	public String detail(Model model, @PathVariable int id) {
		model.addAttribute("book", bookService.findById(id));
		return "user/books/detail";
	}
}
