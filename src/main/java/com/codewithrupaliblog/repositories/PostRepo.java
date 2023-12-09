package com.codewithrupaliblog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewithrupaliblog.entities.Category;
import com.codewithrupaliblog.entities.Post;
import com.codewithrupaliblog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	//kisi user ke sare post chaiye aur us post ki category
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);	
	
	
	//hibernate version problem
	//dyanamic sab bhej denge
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);

}
