package com.codewithrupaliblog.services;

import java.util.List;

import com.codewithrupaliblog.payload.CategoryDto;

public interface CategoryServices {
	
	//method ke aage public likhane ki jarurat nahi he kyuki interface me by defoult har methods public aur
	//abstract hi hote
	
	
	    // create
		CategoryDto createCategory(CategoryDto categoryDto);
		

		// update
		CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
		

		// delete
		void deleteCategory(Integer categoryId);
		

		// get
		CategoryDto getCategory(Integer categoryId);
		

		// get All
		List<CategoryDto> getCategories();

}
