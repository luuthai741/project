package com.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findByName(String name);
	@Query("SELECT b FROM Book b where b.enabled = TRUE")
	List<Book> findAllByEnabled();
	@Query("SELECT b FROM Book b where b.name LIKE %?1% AND b.enabled = TRUE")
	List<Book> searchBookEnabled(String name);
	@Query("SELECT b FROM Book b where b.name LIKE %?1%")
	List<Book> searchBook(String name);
}
