package com.codewithrupaliblog.services;

import com.codewithrupaliblog.payload.CommentDto;

public interface CommentService {
	
	
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	
	
	

	void deleteComment(Integer commentId);

}
