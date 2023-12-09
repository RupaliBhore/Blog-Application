package com.codewithrupaliblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithrupaliblog.entities.Comments;

public interface CommentRepo extends JpaRepository<Comments, Integer>{
	
	
	

}
