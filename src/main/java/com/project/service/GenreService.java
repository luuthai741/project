package com.project.service;

import java.util.List;

import com.project.entities.Genre;

public interface GenreService extends GenericService<Genre> {
	List<Genre> findAllByEnabled();
	Genre findByName(String name);
}
