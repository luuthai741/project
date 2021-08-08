package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageService<T> {
	Page<T> paging(int pageNumber);
}
