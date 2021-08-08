package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.entities.Genre;
import com.project.repository.GenreRepository;
import com.project.service.GenreService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

	private GenreRepository genreRepo;

	@Override
	public List<Genre> findAll() {
		// TODO Auto-generated method stub
		return genreRepo.findAll();
	}

	@Override
	public Genre findById(int id) {
		// TODO Auto-generated method stub
		return genreRepo.findById(id).orElse(null);
	}

	@Override
	public void save(Genre t) {
		genreRepo.save(t);

	}

	@Override
	public void deleteByID(int id) {
		Genre genre = genreRepo.findById(id).orElse(null);
		if(genre != null) {
			genreRepo.deleteById(id);
		}
	}

	@Override
	public List<Genre> findAllByEnabled() {
		// TODO Auto-generated method stub
		return genreRepo.findAllByEnabled();
	}

	@Override
	public Genre findByName(String name) {
		// TODO Auto-generated method stub
		return genreRepo.findByName(name);
	}

}
