package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	Genre findByName(String name);
	@Query("SELECT g FROM Genre g where g.enabled = TRUE")
	List<Genre> findAllByEnabled();
}
