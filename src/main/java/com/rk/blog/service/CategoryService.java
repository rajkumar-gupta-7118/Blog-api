package com.rk.blog.service;

import java.util.List;

import com.rk.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCat(CategoryDto cd);

	CategoryDto updateCat(CategoryDto cd, Integer id);

	CategoryDto getCatById(Integer id);

	List<CategoryDto> getAllCat();

	void deleteCat(Integer id);

}
