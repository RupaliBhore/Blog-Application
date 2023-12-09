package com.codewithrupaliblog.services;

import java.util.List;

import com.codewithrupaliblog.entities.Post;
import com.codewithrupaliblog.payload.PostDto;
import com.codewithrupaliblog.payload.PostResponse;

public interface PostService {
	

	
	   //create 
		PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

		
		//update 
		//kis post ko upadte karana he usaki id bhe do
		PostDto updatePost(PostDto postDto, Integer postId);
		

		// delete
		//delete ke liye hume sirf id chaiye post ki aur ye kuch return nahi karega
		void deletePost(Integer postId);
		
		
		
		//get all posts
		PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
		//PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,);
		//List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
		
		
		
		//get single post
		PostDto getPostById(Integer postId);
		
		
		
		//get all posts by category
		//category ke sare post chiye
		List<PostDto> getPostsByCategory(Integer categoryId);
		
		
		
		//get all posts by user
		List<PostDto> getPostsByUser(Integer userId);
		
		
		
		//search posts
		//ye method keywords se post ko search karega aur list provude karega
		List<PostDto> searchPosts(String keyword);


}
