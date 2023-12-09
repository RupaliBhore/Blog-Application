package com.codewithrupaliblog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codewithrupaliblog.entities.Comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//post ke data ke liye postDto banate he  post ka sara data usake object me hoga
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
   private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;	
	
	private CategoryDto category;

	private UserDto user;
	
	
	//jab hum post ko featch karange to us post ke comment apane aap aa jayenge alag se comment featch karne ki
	//jarurat nahi he comment post ke saat featch karenge  comment ki id aur uska conent dikhiya
	private Set<CommentDto> comments=new HashSet<>();

}
