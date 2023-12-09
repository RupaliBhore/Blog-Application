package com.codewithrupaliblog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithrupaliblog.entities.Category;
import com.codewithrupaliblog.expections.ResourceNotFoundException;
import com.codewithrupaliblog.payload.CategoryDto;
import com.codewithrupaliblog.repositories.CategoryRepo;
import com.codewithrupaliblog.services.CategoryServices;


@Service
public class CategoryServiceImpl  implements CategoryServices{
	
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Autowired
	private ModelMapper modelMapper;
	

	//CREATE CATEGORY
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}
	
	

	   //UPADTE CATEGORY
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		//pahile category ko get karo id se  agar databse me catory nahi hogi o to excetion aayai
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));

		//set karo category ko
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedcat = this.categoryRepo.save(cat);

		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}
	
	
	//DELETE CATEGORY
	@Override
	public void deleteCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "category id", categoryId));
		
		//delete karo get kiye huve id ko
		this.categoryRepo.delete(cat);
	}
	
	
      
	//GET SINGLE CATEGORY
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		return this.modelMapper.map(cat, CategoryDto.class);
	}
	
	
	
	
	//GET ALL CATEGORY

	@Override
	public List<CategoryDto> getCategories() {

		List<Category> categories = this.categoryRepo.findAll();
		
		//collector ki madat se collect karenge
		List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());

		return catDtos;
	}
	
	
	
	

}
