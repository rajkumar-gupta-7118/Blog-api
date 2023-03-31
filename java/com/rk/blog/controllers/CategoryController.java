package com.rk.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.blog.payloads.CategoryDto;
import com.rk.blog.payloads.ResponseApi;

import com.rk.blog.service.CategoryService;

@RestController
@RequestMapping("/apis/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**@apiNote This API is used to Create Category
	 * @author Rajkumar
	 * @since 1.0
	 * @param cd
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto cd) {

		CategoryDto cdto = categoryService.createCat(cd);

		return new ResponseEntity<CategoryDto>(cdto, HttpStatus.CREATED);

	}

	/**@apiNote This API is used to Update Category
	 * @author Rajkumar
	 * @since 1.0
	 * @param cd
	 * @param id
	 * @return
	 */

	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto cd, @PathVariable Integer id) {

		CategoryDto updateCat = categoryService.updateCat(cd, id);

		return ResponseEntity.ok(updateCat);

	}

	/**@apiNote This API is used to get Category by Id
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<CategoryDto> getcategory(@PathVariable Integer id) {

		CategoryDto catById = categoryService.getCatById(id);

		return ResponseEntity.ok(catById);

	}

	/**
	 * @apiNote This API is used to get all Category
	 * @author Rajkumar
	 * @since 1.0
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		return new ResponseEntity<List<CategoryDto>>(categoryService.getAllCat(), HttpStatus.OK);

	}

	/**@apiNote This API is used to delete Category 
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseApi> deleteCategory(@PathVariable Integer id) {

		categoryService.deleteCat(id);

		return new ResponseEntity<ResponseApi>(new ResponseApi("SuccessFully Deleted"), HttpStatus.OK);

	}

}
