package com.project.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.project.service.BookService;
import com.project.service.GenreService;
import com.project.ultil.UploadFileUtil;

import lombok.AllArgsConstructor;

@Controller("adminBookController")
@RequestMapping("/admin/books")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class BookController {
	
	private BookService bookService;
	private GenreService genreService;
	private UploadFileUtil uploadFile;
	
	@GetMapping("/page={currentPage}")
	public String page(Model model, @PathVariable("currentPage") int currentPage) {
		Page<Book> page = bookService.paging(currentPage);
		List<Book> books = page.getContent();
		model.addAttribute("books", books);
		model.addAttribute("page", currentPage);
		model.addAttribute("totalItem", page.getTotalElements());
		model.addAttribute("totalPage", page.getTotalPages());
		return "admin/books/index";
	}
	
	@GetMapping("/{id}")
	public String detail(Model model,@PathVariable int id) {
		model.addAttribute("book", bookService.findById(id));
		return "admin/books/detail";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		Book book = new Book();
		
		model.addAttribute("book", book);
		model.addAttribute("genres", genreService.findAll());
		return "admin/books/form";	
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute(name = "book") Book book,
			@RequestParam("fileImage") MultipartFile multipartFile) {
		String originFileName = multipartFile.getOriginalFilename();
		String fileName  = StringUtils.cleanPath(originFileName);
		book.setImage(fileName);
		bookService.save(book);
		String uploadDir = "./book-images/";
		uploadFile.saveFile(uploadDir, multipartFile, fileName);
		
		return "redirect:/admin/books/page=1";
	}
	@GetMapping("/update/{id}")
	public String update(Model model,@PathVariable int id) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
		model.addAttribute("genres", genreService.findAll());
		return "admin/books/form";	
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute(name = "book") Book book,
			@RequestParam("fileImage") MultipartFile multipartFile) {
		String originFileName = multipartFile.getOriginalFilename();
		String fileName  = StringUtils.cleanPath(originFileName);
		book.setImage(fileName);
		bookService.save(book);
		String uploadDir = "./book-images/";
		uploadFile.saveFile(uploadDir, multipartFile, fileName);
		return "redirect:/admin/books/page=1";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(Model model,@PathVariable int id) {
		bookService.deleteByID(id);
		return "redirect:/admin/books/page=1";
	}
	
}
