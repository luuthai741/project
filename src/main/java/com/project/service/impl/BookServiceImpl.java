package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.entities.Book;
import com.project.repository.BookRepository;
import com.project.service.BookService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {
	private BookRepository bookRepo;

	@Override
	public List<Book> findAll() {
		return bookRepo.findAll();
	}

	@Override
	public Book findById(int id) {
		return bookRepo.findById(id).orElse(null);
	}

	@Override
	public void save(Book t) {
		bookRepo.save(t);
	}

	@Override
	public void deleteByID(int id) {
		Book book = bookRepo.findById(id).orElse(null);
		if (book != null) {
			bookRepo.deleteById(id);
		}
	}

	@Override
	public List<Book> findAllByEnabled() {
		return bookRepo.findAllByEnabled();
	}

	@Override
	public Book findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> searchBookEnabled(String name) {
		// TODO Auto-generated method stub
		return bookRepo.searchBookEnabled(name);
	}

	@Override
	public List<Book> searchBook(String name) {
		// TODO Auto-generated method stub
		return bookRepo.searchBook(name);
	}

	@Override
	public Page<Book> paging(int pageNumber) {
		Sort sort= Sort.by("name").ascending();
		Pageable pageable = PageRequest.of(pageNumber - 1 ,5 , sort);
		return bookRepo.findAll(pageable);
	}

}
