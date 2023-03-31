package com.rk.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.blog.entities.Category;
import com.rk.blog.exceptions.ResourceNotFoundException;
import com.rk.blog.payloads.CategoryDto;
import com.rk.blog.repositories.CategoryRepo;
import com.rk.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCat(CategoryDto cd) {

		Category category = modelMapper.map(cd, Category.class);

		Category Cat2 = categoryRepo.save(category);

		return modelMapper.map(Cat2, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCat(CategoryDto cd, Integer id) {

		Category cat = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", id));

		cat.setCatName(cd.getCatName());
		cat.setCatDescription(cd.getCatDescription());

		Category category = categoryRepo.save(cat);

		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto getCatById(Integer id) {

		Category cat = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", id));

		return modelMapper.map(cat, CategoryDto.class);

	}

	@Override
	public List<CategoryDto> getAllCat() {

		List<Category> list = categoryRepo.findAll();

		List<CategoryDto> list2 = list.stream().map((cat) -> modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());

		return list2;
	}

	@Override
	public void deleteCat(Integer id) {

		if (categoryRepo.existsById(id)) {

			categoryRepo.deleteById(id);

		}

	}

}
