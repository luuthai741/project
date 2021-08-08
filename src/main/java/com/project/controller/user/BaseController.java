package com.project.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.entities.Genre;
import com.project.service.GenreService;

@Controller
public class BaseController {
	
	@Autowired
	private GenreService genreService;
	@ModelAttribute("genres")
	public List<Genre> menus(){
		return genreService.findAllByEnabled();
	}
}
