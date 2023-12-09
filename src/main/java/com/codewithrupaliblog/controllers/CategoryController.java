package com.codewithrupaliblog.controllers;

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

import com.codewithrupaliblog.payload.ApiResponse;
import com.codewithrupaliblog.payload.CategoryDto;
import com.codewithrupaliblog.services.CategoryServices;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	

	@Autowired
	private CategoryServices categoryService;
	
	
	
	

	// CREATE CATEGORY
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto cateogDto) {
		CategoryDto createCategory = this.categoryService.createCategory(cateogDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	
	
	

	// UPDATE CATEGORY
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId)
	{
		//updtede category milegi
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
		
		//updated category delete karo
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	
	
	// DELETE CATEGORY
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully !!", true),
				HttpStatus.OK);
	}
	
	
	
	//GET SINGLE CATEGORY
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {

		CategoryDto categoryDto = this.categoryService.getCategory(catId);

		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}
	
	
	

	//GET ALL CATEGORY
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categories = this.categoryService.getCategories();
		return ResponseEntity.ok(categories);
	}

}
